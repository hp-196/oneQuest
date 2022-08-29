package com.oneqst.quest.repository;

import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AuthPostRepository extends JpaRepository<AuthPost, Long> {
    List<AuthPost> findByQuest(Quest quest);
}
