package com.oneqst.quest.repository;

import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(QuestPost questPost);

}
