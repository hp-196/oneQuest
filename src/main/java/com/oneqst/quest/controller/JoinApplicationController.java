package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.service.JoinApplicationService;
import com.oneqst.quest.repository.JoinApplicationRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinApplicationController {

    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final JoinApplicationRepository joinApplicationRepository;
    private final JoinApplicationService applicationService;
    private final QuestService questService;

    /**
     * 가입 신청 관리 페이지
     */
    @GetMapping("/quest/{url}/setting/join/application")
    public String join(@CurrentUser Member member, @PathVariable String url, Model model) {
        applicationService.goPage(member, url, model);
        return "quest/setting/joinApplication";
    }

    /**
     * 가입 신청
     */
    @GetMapping("/quest/{url}/join/application/{nickname}")
    public String applicationJoin(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname, Model model) {
        String alertMessage = applicationService.joinApplication(member, url, nickname, model);
        if (alertMessage != null) return alertMessage;
        return "alertMessage";
    }

    /**
     * 가입 신청 수락
     */
    @GetMapping("/quest/{url}/join/application/accept/{nickname}")
    public String joinAccept(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        applicationService.acceptApplication(member, url, nickname);
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/"+encodedUrl+"/setting/join/application";
    }

    /**
     * 가입 신청 거절
     */
    @GetMapping("/quest/{url}/join/application/cancel/{nickname}")
    public String joinCancel(@CurrentUser Member member, @PathVariable String url, @PathVariable String nickname) throws UnsupportedEncodingException {
        applicationService.cancelApplication(member, url, nickname);
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/"+encodedUrl+"/setting/join/application";
    }

    /**
     * 가입 상태 변경
     */
    @GetMapping("/quest/{url}/application/setting/state/change")
    public String stateChange1(@CurrentUser Member member, @PathVariable String url) throws UnsupportedEncodingException {
        applicationService.stateChange(member, url);
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/"+encodedUrl+"/setting/join/application";
    }


}
