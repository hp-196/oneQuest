package com.oneqst.quest.repository;

import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.repository.query.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByPost(QuestPost questPost);

    List<Comment> findByAuthPost(AuthPost authPost);
}
