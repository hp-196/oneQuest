package com.oneqst.quest.repository;

import com.oneqst.quest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QuestRepository extends JpaRepository<Quest, Long> {


    Quest findByQuestUrl(String url);

}
