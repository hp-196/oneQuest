package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.CommentDto;
import com.oneqst.quest.event.CommentNotice;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final QuestPostRepository questPostRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 해당 포스팅 댓글 전체 조회
     */
    public List<Comment> findCommentAll(Long id) {
        QuestPost questPost = questPostRepository.getById(id);
        List<Comment> commentList = commentRepository.findByPost(questPost);
        return commentList;
    }

    /**
     * 해당 포스팅 댓글 전체 조회
     */
    public List<Comment> findCommentAuth(AuthPost authPost) {
        List<Comment> commentList = commentRepository.findByAuthPost(authPost);
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
                .postTime(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
        if (!member.equals(questPost.getWriter())) {
            eventPublisher.publishEvent(new CommentNotice(comment, questPost));
        }
    }

    /**
     * auth 댓글 POST 실험용
     */
    public void authCommentPost(Member member, AuthPost authPost, CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .writer(member)
                .authPost(authPost)
                .postTime(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Member member, QuestPost questPost, Comment comment) {
        commentRepository.delete(comment);
    }

    /**
     * auth 댓글 삭제
     */
    public void deleteAuthComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
