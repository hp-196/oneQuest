package com.oneqst.quest.repository;

import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByQuest(Quest quest);
}
