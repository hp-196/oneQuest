package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.dto.AuthPostUpdateDto;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final QuestRepository questRepository;
    private final AuthPostRepository authPostRepository;
    private final AuthService authService;

    /**
     * 퀘스트 인증 포스팅 GET
     */
    @GetMapping("/quest/{url}/auth/post")
    public String questAuth(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest == null) {
            throw new IllegalArgumentException("스터디 없음");
        }

        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new AuthPostDto());
        return "quest/auth-posting";
    }

    /**
     * 퀘스트 인증 포스팅 POST
     */
    @PostMapping("/quest/{url}/auth/post")
    public String questAuthPost(@CurrentUser Member member, @PathVariable String url,
                                @Valid AuthPostDto authPostDto) {
        Quest quest = questRepository.findByQuestUrl(url);
        authService.AuthPost(authPostDto, quest, member);
        return "redirect:/quest/" + url;
    }

    /**
     * 퀘스트 인증 페이지 조회
     */
    @GetMapping("/quest/{url}/auth/post/{id}")
    public String getAuthPost(@CurrentUser Member member, @PathVariable String url,
                              @PathVariable Long id, Model model) {
        AuthPost authPost = authPostRepository.getOne(id);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute(authPost);
        model.addAttribute(member);
        return "quest/auth-view";
    }

    /**
     * 퀘스트 인증 페이지 수정
     */
    @GetMapping("/quest/{url}/auth/post/{id}/update")
    public String updateAuthPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id, Model model) {
        AuthPost authPost = authPostRepository.getOne(id);
        model.addAttribute(member);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute(authPost);
        model.addAttribute(new AuthPostUpdateDto(authPost));
        return "quest/auth-update";
    }

    /**
     * 퀘스트 인증 페이지 수정
     */
    @PostMapping("/quest/{url}/auth/post/{id}/update")
    public String updateAuthPosting(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                                    @Valid AuthPostUpdateDto authPostUpdateDto) {
        AuthPost authPost = authPostRepository.getById(id);
        if (member.equals(authPost.getWriter())) {
            authService.updateAuthPost(authPost, authPostUpdateDto);;
        }
        return "redirect:/quest/" + url + "/auth/post/" + id;
    }

    /**
     * 퀘스트 인증 페이지 삭제
     */
    @DeleteMapping("/quest/{url}/auth/post/{id}/delete")
    public String deleteAuthPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id) {
        AuthPost authPost = authPostRepository.getById(id);
        if (member.equals(authPost.getWriter())) {
            authService.deletePost(authPost);
        }
        return "redirect:/quest/" + url;
    }


}
