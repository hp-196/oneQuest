package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import com.oneqst.quest.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestSettingController {

    private final QuestRepository questRepository;
    private final SettingService settingService;
    private final QuestService questService;
    private final MemberRepository memberRepository;

    /**
     * 불건전한 퀘스트 접근 예외
     */
    private void extracted(Member member, Quest quest) {
        if (!quest.isHostOrMaster(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
    }
    /**
     * 퀘스트 멤버 설정
     */
    @GetMapping("/quest/{url}/setting/auth")
    public String questSetting(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute("masterList",quest.getQuestMaster());
        model.addAttribute("memberList",quest.getQuestMember());
        return "quest/setting/auth";
    }

    /**
     * 모집 여부 설정
     */
    @GetMapping("/quest/{url}/setting/recruitment")
    public String questRecruitment(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        return "quest/setting/recruitment";
    }

    /**
     * 모집 여부 처리 url
     */
    @GetMapping("/quest/{url}/recruitment")
    public String recruitmentOn(@CurrentUser Member member, @PathVariable String url) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            return "redirect:/quest/" + quest.encodedUrl() + "/setting/recruitment";
        }
        settingService.recruitment(quest);
        return "redirect:/quest/" + quest.encodedUrl() + "/setting/recruitment";

    }

    /**
     * 멤버 -> 매니저 승격
     */
    @GetMapping("/quest/{url}/add/manager/{nickname}")
    public String addManager(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            log.info("승격 실패");
            return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
        }
        Member questMember = memberRepository.findByNickname(nickname);
        settingService.addManager(quest,questMember);
        return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
    }

    /**
     * 매니저 -> 멤버 강등
     */
    @GetMapping("/quest/{url}/relegation/member/{nickname}")
    public String relegation(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.getQuestHost().equals(member)) {
            log.info("강등 실패");
            return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
        }
        Member manager = memberRepository.findByNickname(nickname);
        settingService.relegation(quest, manager);
        return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
    }

    /**
     * 호스트 임명
     */
    @GetMapping("/quest/{url}/assign/{nickname}")
    public String assignHost(@CurrentUser Member host, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.getQuestHost().equals(host)) {
            return "redirect:/quest/"+ quest.encodedUrl();
        }
        Member manager = memberRepository.findByNickname(nickname);
        settingService.assignHost(quest,host,manager);
        return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
    }

    /**
     * 회원 추방
     */
    @GetMapping("/quest/{url}/exile/{nickname}")
    public String exileMember(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            log.info("추방 실패");
            return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
        }
        Member questMember = memberRepository.findByNickname(nickname);
        questService.questWithdraw(quest,questMember);
        return "redirect:/quest/"+ quest.encodedUrl() + "/setting/auth";
    }

    /**
     * 호스트 전용 퀘스트 삭제
     */
    @GetMapping("/quest/{url}/delete")
    public String deleteQuest(@CurrentUser Member member, @PathVariable String url) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.getQuestHost().equals(member)) {
            return "redirect:/quest/"+ quest.encodedUrl();
        }
        settingService.deleteQuest(quest);
        return "redirect:/";
    }




}
