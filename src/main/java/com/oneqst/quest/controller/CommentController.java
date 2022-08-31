package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.CommentDto;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.service.CommentService;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final QuestPostRepository questPostRepository;
    private final CommentRepository questCommentRepository;
    private final CommentService commentService;

    /**
     * 퀘스트 댓글 포스팅
     */
    @PostMapping("/quest/{url}/post/{id}/comment")
    public String commentPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                              @Valid CommentDto commentDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            log.info("댓글 작성 실패");
            return "redirect:/quest/" + url + "/post/" + id;
        }
        QuestPost questPost = questPostRepository.getOne(id);
        commentService.commentPost(member, questPost, commentDto);
        return "redirect:/quest/" + url + "/post/" + id;
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/quest/{url}/post/{id}/comment/delete/{commentId}")
    public String deleteComment(@CurrentUser Member member, @PathVariable String url,
                                @PathVariable Long id, @PathVariable Long commentId) {
        QuestPost questPost = questPostRepository.getById(id);
        Comment comment = questCommentRepository.getById(commentId);
        commentService.deleteComment(member, questPost, comment);
        return "redirect:/quest/" + url + "/post/" + id;
    }
}
