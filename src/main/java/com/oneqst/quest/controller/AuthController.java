package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final QuestRepository questRepository;
    private final QuestService questService;

    /**
     * 퀘스트 인증 페이지 조회
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
     * 퀘스트 인증 포스팅
     */
    @PostMapping("/quest/{url}/auth/post")
    public String questAuthPost(@CurrentUser Member member, @PathVariable String url,
                                @Valid AuthPostDto authPostDto) {
        Quest quest = questRepository.findByQuestUrl(url);
        questService.AuthPost(authPostDto, quest, member);
        return "redirect:/quest/" + url;
    }
}
