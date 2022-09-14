package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScoreController {

    private final QuestRepository questRepository;
    private final AuthPostRepository authPostRepository;
    private final AuthService authService;

    @GetMapping("/quest/{url}/auth/{id}/score")
    public String getScore(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id) {
        AuthPost authPost = authPostRepository.getById(id);
        Quest quest = questRepository.findByQuestUrl(url);
        authService.plusScore(member, authPost, quest, 5);
        return "redirect:/quest/" + url;
    }
}