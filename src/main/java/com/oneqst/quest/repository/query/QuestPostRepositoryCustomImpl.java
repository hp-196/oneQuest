package com.oneqst.quest.repository.query;

import com.oneqst.quest.dto.MyQuestPostDto;
import com.oneqst.quest.dto.QMyQuestPostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QQuest.quest;
import static com.oneqst.quest.domain.QQuestPost.questPost;

public class QuestPostRepositoryCustomImpl implements QuestPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public QuestPostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MyQuestPostDto> myQuestPostLookup(Long memberId) {
        return queryFactory
                .select(new QMyQuestPostDto(
                        quest.questUrl,
                        quest.questTitle,
                        questPost.id,
                        questPost.title,
                        questPost.content,
                        questPost.postTime
                ))
                .distinct()
                .from(quest, questPost)
                .join(quest.questMember, member)
                .join(questPost.quest, quest)
                .where(questPost.writer.id.eq(memberId))
                .fetch();
    }
}
