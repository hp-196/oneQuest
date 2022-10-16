package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final QuestRepository questRepository;

    @GetMapping("/search/list")
    public String search(@CurrentUser Member member,
                         @PageableDefault(size = 9) Pageable pageable,
                         @RequestParam(value = "title") String title, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        if (member == null) {
            log.info("멤버가 없어서 로그인페이지 리다이렉트");
            return "login";
        }
        model.addAttribute("searchTitle", title);
//        model.addAttribute("questList", questRepository.searchPaging(member, title, pageable));
        model.addAttribute("questList", questRepository.totalSearchPaging(member, pageable, title));
        return "search";
    }
}