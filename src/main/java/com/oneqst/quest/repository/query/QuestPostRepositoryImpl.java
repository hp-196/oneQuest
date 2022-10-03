package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.QMember;
import com.oneqst.quest.domain.QQuest;
import com.oneqst.quest.domain.QQuestPost;
import com.oneqst.quest.dto.MyQuestPostDto;
import com.oneqst.quest.dto.QMyQuestPostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QQuest.*;
import static com.oneqst.quest.domain.QQuestPost.*;

public class QuestPostRepositoryImpl implements QuestPostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestPostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MyQuestPostDto> myQuestPost(Long memberId) {
        return queryFactory
                .select(new QMyQuestPostDto(
                        quest.questTitle,
                        questPost.title,
                        questPost.content,
                        questPost.postTime
                ))
                .from(quest, questPost)
                .join(quest.questMember, member)
                .join(questPost.quest, quest)
                .where(questPost.writer.id.eq(memberId))
                .distinct()
                .fetch();
    }
}
