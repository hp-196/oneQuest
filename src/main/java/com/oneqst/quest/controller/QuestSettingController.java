package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestSettingController {

    private final QuestRepository questRepository;
    /**
     * 퀘스트 설정
     */
    @GetMapping("/quest/{url}/setting/auth")
    public String questSetting(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);

        model.addAttribute(member);
        model.addAttribute(quest);
        return "quest/setting/auth";
    }
}
