package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.MyQuestDto;
import com.oneqst.quest.dto.QMyQuestDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QQuest.quest;

public class QuestRepositoryCustomImpl implements QuestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public QuestRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
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
        QueryResults<Quest> result = queryFactory
                .selectFrom(quest)
                .where(quest.questTitle.contains(title).and(quest.questMember.contains(member).not()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /**
     * 나의 퀘스트 활동 목록 조회
     * @param memberId  유저의 Id
     * @return  해당 유저의 퀘스트 활동 리스트
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
                .join(quest.questMember, member).fetchJoin()
                .where(member.id.eq(memberId))
                .orderBy(quest.questStartTime.desc())
                .fetch();
    }
}