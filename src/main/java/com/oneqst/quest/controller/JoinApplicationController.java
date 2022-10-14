package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.config.AlertMessage;
import com.oneqst.quest.domain.JoinApplication;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.event.JoinApplicationService;
import com.oneqst.quest.repository.JoinApplicationRepository;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinApplicationController {

    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final JoinApplicationRepository joinApplicationRepository;
    private final JoinApplicationService applicationService;

    /**
     * 가입 신청 관리 페이지
     */
    @GetMapping("/quest/{url}/setting/join/application")
    public String join(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        List<JoinApplication> list = joinApplicationRepository.findByQuest(quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute("applicationList",list);
        return "quest/setting/joinApplication";
    }

    /**
     * 가입 신청
     */
    @GetMapping("/quest/{url}/join/application/{nickname}")
    public String applicationJoin(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest.isQuestRecruitEnd() == false) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        Member joinMember = memberRepository.findByNickname(nickname);
        applicationService.newWaitingMember(quest, joinMember);
        model.addAttribute("data", new AlertMessage("가입 신청이 완료되었습니다. 수락이 완료되면 알림을 보내드립니다.", "/quest/"+url));
        return "alertMessage";
    }

    /**
     * 가입 상태 변경
     */
    @GetMapping("/quest/{url}/application/setting/state/change")
    public String stateChange(@CurrentUser Member member, @PathVariable String url) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.getQuestHost().equals(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        applicationService.changeState(quest);
        return "redirect:/quest/"+url+"/setting/join/application";
    }
}
