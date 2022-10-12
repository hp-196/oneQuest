package com.oneqst.quest.repository.query;

import com.oneqst.quest.dto.MyAuthDto;
import com.oneqst.quest.dto.QMyAuthDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneqst.Member.domain.QMember.member;
import static com.oneqst.quest.domain.QAuthPost.authPost;
import static com.oneqst.quest.domain.QQuest.quest;

public class AuthPostRepositoryCustomImpl implements AuthPostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public AuthPostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 유저의 퀘스트 인증을 조회한다.
     * @param memberId  해당 유저의 Id
     * @return  해당 유저의 퀘스트 인증 리스트
     */
    @Override
    public List<MyAuthDto> myActivityAuthPostLookup(Long memberId) {
        return queryFactory
                .select(new QMyAuthDto(
                        quest.questUrl,
                        quest.questTitle,
                        authPost.id,
                        authPost.title,
                        authPost.content,
                        authPost.postTime
                ))
                .distinct()
                .from(quest, authPost)
                .join(quest.questMember, member)
                .join(authPost.quest, quest)
                .where(authPost.writer.id.eq(memberId))
                .orderBy(authPost.postTime.desc())
                .fetch();
    }
}
