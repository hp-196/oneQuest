package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.repository.query.QuestPostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestPostRepository extends JpaRepository<QuestPost, Long>, QuestPostRepositoryCustom {
    List<QuestPost> findByQuest(Quest quest);

    List<QuestPost> findByWriter(Member member);

    List<QuestPost> findByQuestOrderByPostTimeDesc(Quest quest);
}