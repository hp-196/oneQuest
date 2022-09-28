package com.oneqst.quest.repository.query;

import com.oneqst.quest.domain.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QComment.comment;

public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> myComment(Long memberId) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.writer, member).fetchJoin()
                .where(
                        writerEq(memberId)
                ).fetch();
    }

    public static BooleanExpression writerEq(Long memberId) {
        return memberId != null ? comment.writer.id.eq(memberId) : null;
    }
}