package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.InviteDto;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final QuestRepository questRepository;
    private final QuestService questService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(@CurrentUser Member member, Model model) {
        if (member == null) {
            return "login";
        }
        Member findMember = memberRepository.findById(member.getId()).get();
        model.addAttribute("member", findMember);
        model.addAttribute("questList", questRepository.myQuests(findMember.getId()));
        model.addAttribute("notQuestList", questRepository.findRandom9(findMember));
        model.addAttribute("tagQuestList", questRepository.myTagsQuest(findMember));
        model.addAttribute(new InviteDto());
        log.info(findMember.getNickname()+"이 메인화면 접근");
        return "index";
    }

    /**
     * 임의 퀘스트 추가
     */
    @GetMapping("/add")
    public String addData(@CurrentUser Member member) {
        Random random = new Random();
        //진행중 퀘스트
        for (int i = 0; i < 30; i++) {
            Quest quest = Quest.builder()
                    .questTitle("now" + random.nextInt(5000))
                    .questExplain("explain")
                    .questHost(member)
                    .questMaster(new ArrayList<>())
                    .questMember(new ArrayList<>())
                    .questStartTime(LocalDate.now())
                    .questEndTime(LocalDate.now().plusDays(random.nextInt(50)))
                    .questUrl("url" + String.valueOf(random.nextInt(5000)))
                    .questRecruitEnd(true)
                    .build();
            quest.addQuestMember(member);
            questRepository.save(quest);
        }
        //종료 퀘스트
        for (int j = 0; j < 30; j++) {
            Quest quest = Quest.builder()
                    .questTitle("the end" + random.nextInt(5000))
                    .questExplain("explain")
                    .questHost(member)
                    .questMaster(new ArrayList<>())
                    .questMember(new ArrayList<>())
                    .questStartTime(LocalDate.now().minusDays(random.nextInt(50)))
                    .questEndTime(LocalDate.now().minusDays(1))
                    .questUrl("url" + String.valueOf(random.nextInt(5000)))
                    .questRecruitEnd(true)
                    .build();
            quest.addQuestMember(member);
            questRepository.save(quest);
        }
        return "redirect:/";
    }

    /**
     * 퀘스트 3개 추가
     */
    @GetMapping("/three")
    public String addThree(@CurrentUser Member member) {
        Random random = new Random();
        //진행중 퀘스트
        for (int i = 0; i < 3; i++) {
            Quest quest = Quest.builder()
                    .questTitle("hello" + random.nextInt(5000))
                    .questExplain("explain")
                    .questHost(member)
                    .questStartTime(LocalDate.now().minusDays(3))
                    .questEndTime(LocalDate.now().plusDays(random.nextInt(50)))
                    .questUrl("url" + String.valueOf(random.nextInt(5000)))
                    .questMaster(new ArrayList<>())
                    .questMember(new ArrayList<>())
                    .questRecruitEnd(true)
                    .build();
            quest.addQuestMember(member);
            questRepository.save(quest);
        }
        return "redirect:/";
    }
}