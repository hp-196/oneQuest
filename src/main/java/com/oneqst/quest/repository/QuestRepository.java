package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.query.QuestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface QuestRepository extends JpaRepository<Quest, Long>, QuestRepositoryCustom {

    List<Quest> findByQuestMemberContaining(Member member);

    List<Quest> findByQuestMemberEqualsAndQuestTitleIsContaining(Member member, String str);
}

