package com.oneqst.quest.repository;

import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface QuestPostRepository extends JpaRepository<QuestPost, Long> {


    List<QuestPost> findByQuest(Quest quest);

}
