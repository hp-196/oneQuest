package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final QuestRepository questRepository;
    private final QuestService questService;

    @GetMapping("/search/list")
    public String search(@RequestParam(value="q") String query, @CurrentUser Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        if (member == null) {
            log.info("멤버가 없어서 로그인페이지 리다이렉트");
            return "login";
        }
        model.addAttribute("questList", questRepository.Search(member.getId(), query));
//        model.addAttribute("notQuestList", questRepository.other_quests(member.getId()));
        return "search";
    }
}