package com.oneqst.Member.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findByEmail(String email);

    Member findByNickname(String nickname);



}