package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.domain.QMember;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.QuestRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<Quest> search(String nickname, String title) {
        return queryFactory
                .selectFrom(quest)
                .where(
                        quest.questTitle.contains(title),
                        member.nickname.eq(nickname))
                .fetch();
    }

    // 수정필요
    @Override
    public List<Quest> total_search(Long member_id) {
        return queryFactory
                .selectFrom(quest)
                .fetch();
    }
}