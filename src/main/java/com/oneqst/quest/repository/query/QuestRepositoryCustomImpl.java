package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.domain.QQuest;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.MyQuestDto;
import com.oneqst.quest.dto.QMyQuestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QQuest.quest;

public class QuestRepositoryCustomImpl implements QuestRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    public QuestRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
    }

    public static BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? quest.questTitle.contains(title) : null;
    }

    @Override
    public List<Quest> myQuests(Long memberId) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMember, member).fetchJoin()
                .where(
                        member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<Quest> otherQuests(Long memberId) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMember, member).fetchJoin()
                .where(member.id.ne(memberId))
                .fetch();
    }

    @Override
    public List<Quest> search(Long memberId, String title) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMember, member).fetchJoin()
                .where(
                        member.id.eq(memberId),
                        titleContains(title)
                )
                .fetch();
    }

    @Override
    public List<Quest> questMaster(Long memberId) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMaster, member).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<Quest> questMember(Long memberId) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMaster, member).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }

    @Override
    public Page<Quest> searchPaging(Member member, String title, Pageable pageable) {
        List<Quest> result = queryFactory
                .selectFrom(quest)
                .where(quest.questTitle.contains(title)
                        .or(quest.tags.any().title.contains(title))
                        .and(quest.questMember.contains(member).not()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(quest.count())
                .from(quest)
                .where(quest.questTitle.contains(title)
                        .or(quest.tags.any().title.contains(title))
                        .and(quest.questMember.contains(member).not()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
//        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /**
     * 참여중 X, 회원 모집중, 진행중인
     * 무작위 퀘스트 9개 추출
     * https://stackoverflow.com/questions/15869279/does-querydsl-not-support-rand
     */
    @Override
    public List<Quest> findRandom9(Member member) {
        JPAQuery<Quest> query = new JPAQuery<>(entityManager, MySqlJpaTemplates.DEFAULT);
        QQuest qQuest = new QQuest("quest");
        return query.from(qQuest)
                .where(qQuest.questMember.contains(member).not()
                        .and(qQuest.questRecruitEnd.eq(true))
                        .and(qQuest.questStartTime.before(LocalDate.now()))
                        .and(qQuest.questEndTime.after(LocalDate.now())))
                .orderBy(NumberExpression.random().asc())
                .limit(9)
                .fetch();
    }

    /**
     * 나의 퀘스트 활동 목록 조회
     *
     * @param memberId 유저의 Id
     * @return 해당 유저의 퀘스트 활동 리스트
     */
    @Override
    public List<MyQuestDto> myActivityQuestLookup(Long memberId) {
        return queryFactory
                .select(new QMyQuestDto(
                        quest.questUrl,
                        quest.questTitle,
                        quest.questIntroduce,
                        quest.questStartTime,
                        quest.questEndTime
                ))
                .from(quest)
                .join(quest.questMember, member)
                .where(member.id.eq(memberId))
                .orderBy(quest.questStartTime.desc())
                .fetch();
    }

    @Override
    public Page<Quest> totalSearchPaging(Member member, Pageable pageable, String query) {
        String[] queryArr = query.split(" ");
        List<String> titleList = new ArrayList<>();
        titleList.add("");
        List<String> memberList = new ArrayList<>();
        memberList.add("");
        List<String> tagList = new ArrayList<>();
        tagList.add("");
        for (String s : queryArr) {
            String type = searchType(s);
            if (type.equals("title")) {
                titleList.add(s);
            } else if (type.equals("member")) {
                memberList.add(s.replace("@", ""));
            } else {
                tagList.add(s);
            }
        }

        Logger.getLogger("for 문 정상작동");

        List<Quest> content = queryFactory
                .select(quest)
                .from(quest)
                .where(
                        containTitle(titleList).and(quest.questMember.contains(member).not()),
                        eqTag(tagList).and(quest.questMember.contains(member).not())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(NumberExpression.random().asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(quest.count())
                .from(quest)
                .where(
                        containTitle(titleList).and(quest.questMember.contains(member).not()),
                        eqTag(tagList).and(quest.questMember.contains(member).not())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /**
     * 검색 타입 확인기
     *
     * @param s 검색 문자
     * @return 검색 타입
     */
    public String searchType(String s) {
        Matcher tagMatcher = Pattern.compile("(#[A-Za-z가-힣]*)").matcher(s);
        Matcher memberMatcher = Pattern.compile("(@[0-9a-zA-Z가-힣]{2,50})").matcher(s);
        if (tagMatcher.find()) {
            return "tag";
        } else if (memberMatcher.find()) {
            return "member";
        } else {
            return "title";
        }
    }

    /**
     * 제목 포함 검색 조건
     *
     * @param titles 제목 목록
     * @return 제목 검색 조건
     */
    public BooleanBuilder containTitle(List<String> titles) {
        if (titles.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();

        for (String title : titles) {
            builder.or(quest.questTitle.contains(title));
        }

        return builder;
    }

    /**
     * 태그 검색 조건
     *
     * @param tags 태그 목록
     * @return 태그 검색 조건
     */
    public BooleanBuilder eqTag(List<String> tags) {
        if (tags.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();

        for (String tag : tags) {
            builder.and(quest.tags.any().title.eq(tag));
        }
        return builder;
    }

    /**
     * 멤버 검색 조건
     *
     * @param members 멤버 목록
     * @return 멤버 검색 조건
     */
    public BooleanBuilder eqMember(List<String> members) {
        if (members.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();
        for (String member : members) {
            builder.or(quest.questMember.contains(memberRepository.findByNickname(member)));
        }
        return builder;
    }

    public BooleanBuilder nullSafer(BooleanBuilder builder) {
        if (builder.hasValue()) {
            return builder;
        } else {
            return null;
        }
    }
}