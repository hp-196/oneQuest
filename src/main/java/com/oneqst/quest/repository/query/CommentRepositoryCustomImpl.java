package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.QMember;
import com.oneqst.quest.domain.QComment;
import com.oneqst.quest.domain.QQuest;
import com.oneqst.quest.domain.QQuestPost;
import com.oneqst.quest.dto.MyCommentDto;
import com.oneqst.quest.dto.QMyCommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.*;
import static com.oneqst.quest.domain.QComment.*;
import static com.oneqst.quest.domain.QQuest.*;
import static com.oneqst.quest.domain.QQuestPost.*;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MyCommentDto> myCommentLookup(Long memberId) {
        return queryFactory
                .select(new QMyCommentDto(
                        quest.questTitle,
                        questPost.title,
                        comment.content,
                        comment.postTime
                ))
                .distinct()
                .from(quest, questPost, comment)
                .join(quest.questMember, member)
                .join(questPost.quest, quest)
                .join(comment.post, questPost)
                .where(comment.writer.id.eq(memberId))
                .fetch();
    }
}
