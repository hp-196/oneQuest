package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.domain.QMember;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.QuestIndexDto;
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
    public List<Quest> total_quests() {
        return null;
    }

    // TODO 다 긁어온뒤 내 id가 포함된것과 포함되지 않은것을 나누기
    @Override
    public List<Quest> my_quests(Long member_id) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMember, member).fetchJoin()
                .where(member.id.eq(member_id))
                .fetch();
    }

    @Override
    public List<Quest> other_quests(Long member_id) {
        return queryFactory
                .selectFrom(quest)
                .join(quest.questMember, member).fetchJoin()
                .where(member.id.ne(member_id))
                .fetch();
    }
}