package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Random;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final QuestRepository questRepository;
    private final QuestService questService;

    @GetMapping("/")
    public String home(@CurrentUser Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        if (member == null) {
            log.info("멤버가 없어서 로그인페이지 리다이렉트");
            return "login";
        }
        model.addAttribute("questList", questRepository.myQuests(member.getId()));
        model.addAttribute("notQuestList", questRepository.otherQuests(member.getId()));
        return "index";
    }

    /**
     * 임의 퀘스트 추가
     */
    @GetMapping("/add")
    public String addData() {
        Random random = new Random();
        //진행중 퀘스트
        for (int i=0; i<30; i++) {
            Quest quest = Quest.builder()
                    .questTitle("now" + random.nextInt(5000))
                    .questIntroduce("now is run")
                    .questExplain("explain")
                    .questStartTime(LocalDate.now())
                    .questEndTime(LocalDate.now().plusDays(random.nextInt(50)))
                    .questUrl("url"+String.valueOf(random.nextInt(5000)))
                    .build();
            questRepository.save(quest);
        }
        //종료 퀘스트
        for (int j=0; j<30; j++) {
            Quest quest = Quest.builder()
                    .questTitle("the end" + random.nextInt(5000))
                    .questIntroduce("quest is end")
                    .questExplain("explain")
                    .questStartTime(LocalDate.now().minusDays(random.nextInt(50)))
                    .questEndTime(LocalDate.now().minusDays(1))
                    .questUrl("url"+String.valueOf(random.nextInt(5000)))
                    .build();
            questRepository.save(quest);
        }
        return "redirect:/";
    }
}