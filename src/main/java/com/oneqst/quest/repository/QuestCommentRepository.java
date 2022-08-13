package com.oneqst.quest.repository;

import com.oneqst.quest.domain.QuestComment;
import com.oneqst.quest.domain.QuestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestCommentRepository extends JpaRepository<QuestComment, Long> {

    List<QuestComment> findByPost(QuestPost questPost);
}
