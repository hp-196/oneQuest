package com.oneqst.notice;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.notice.QNotice.*;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public NoticeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> myTotalNotice(Long memberId) {
        return queryFactory
                .select(notice.id)
                .from(notice)
                .where(notice.member.id.eq(memberId))
                .fetch();
    }
}
