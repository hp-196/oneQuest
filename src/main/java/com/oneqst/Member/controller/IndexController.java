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
public class IndexController {

    private final QuestRepository questRepository;

    @GetMapping("/")
    public String home(@CurrentUser Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        model.addAttribute("questList", questRepository.findByQuestMemberContaining(member));
        log.info(String.valueOf(model));
        return "index";

    }
}
