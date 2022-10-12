package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.query.AuthPostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AuthPostRepository extends JpaRepository<AuthPost, Long>, AuthPostRepositoryCustom {
    List<AuthPost> findByQuest(Quest quest);

    List<AuthPost> findByQuestOrderByPostTimeDesc(Quest quest);

    List<AuthPost> findByWriter(Member member);
}
