package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberQuestController {
    private final QuestRepository questRepository;

    @GetMapping("/my-quest")
    public String myQuest(@CurrentUser Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        if (member == null) {
            log.info("멤버가 없어서 로그인페이지 리다이렉트");
            return "login";
        }
        model.addAttribute("questList", questRepository.myQuests(member.getId()));
        return "my-quest";
    }
}