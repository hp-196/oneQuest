package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestComment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.CommentDto;
import com.oneqst.quest.repository.QuestCommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestCommentController {

    private final QuestPostRepository questPostRepository;
    private final QuestCommentRepository questCommentRepository;
    private final QuestService questService;

    /**
     * 퀘스트 댓글 포스팅
     */
    @PostMapping("/quest/{url}/post/{id}/comment/post")
    public String commentPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                              @Valid CommentDto commentDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            log.info("댓글 작성 실패");
            return "redirect:/quest/" + url + "/post/" + id;
        }
        QuestPost questPost = questPostRepository.getOne(id);
        QuestComment questComment = questService.commentPost(member, questPost, commentDto);
        return "redirect:/quest/" + url + "/post/" + id;
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/quest/{url}/post/{id}/comment/post/{commentId}")
    public String deleteComment(@CurrentUser Member member, @PathVariable String url,
                                @PathVariable Long id, @PathVariable Long commentId) {
        QuestPost questPost = questPostRepository.getOne(id);
        QuestComment questComment = questCommentRepository.getOne(commentId);
        questService.deleteComment(member, questPost, questComment);
        return "redirect:/quest/" + url + "/post/" + id;
    }
}
