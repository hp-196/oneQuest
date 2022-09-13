package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface QuestRepository extends JpaRepository<Quest, Long> {

    Quest findByQuestUrl(String url);

    List<Quest> findByQuestMasterContaining(Member member);

    List<Quest> findByQuestMemberNotContaining(Member member);

    List<Quest> findByQuestMemberContaining(Member member);
}
