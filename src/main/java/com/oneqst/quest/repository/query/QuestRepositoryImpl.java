package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.*;
import static com.oneqst.quest.domain.QQuest.*;

public class QuestRepositoryImpl implements QuestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public QuestRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Quest> totalQuests() {
        return null;
    }

    // TODO 다 긁어온뒤 내 id가 포함된것과 포함되지 않은것을 나누기
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
    public Page<Quest> searchPaging(Member member, String title, Pageable pageable) {
        QueryResults<Quest> result = queryFactory
                .selectFrom(quest)
                .where(quest.questTitle.contains(title).and(quest.questMember.contains(member).not()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public static BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? quest.questTitle.contains(title) : null;
    }
}