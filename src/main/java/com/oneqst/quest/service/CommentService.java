package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.CommentDto;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final QuestPostRepository questPostRepository;
    private final CommentRepository commentRepository;
    /**
     * 해당 포스팅 댓글 전체 조회
     */
    public List<Comment> findCommentAll(Long id) {
        QuestPost questPost = questPostRepository.getById(id);
        List<Comment> commentList = commentRepository.findByPost(questPost);
        return commentList;
    }

    /**
     * 댓글 POST
     */
    public void commentPost(Member member, QuestPost questPost, CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .writer(member)
                .post(questPost)
                .build();
        member.addCommentList(comment);
        questPost.addCommentList(comment);
        commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Member member, QuestPost questPost, Comment comment) {

        commentRepository.delete(comment);
    }
}
