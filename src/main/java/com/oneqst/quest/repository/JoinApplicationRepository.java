package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.JoinApplication;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface JoinApplicationRepository extends JpaRepository<JoinApplication, Long> {

    List<JoinApplication> findByQuest(Quest quest);

    JoinApplication findByQuestAndMember(Quest quest, Member member);
}
