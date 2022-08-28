package com.oneqst.quest.repository;

import com.oneqst.quest.domain.AuthPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthPostRepository extends JpaRepository<AuthPost, Long> {
}
