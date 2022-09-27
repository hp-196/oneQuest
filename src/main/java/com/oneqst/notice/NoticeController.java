package com.oneqst.notice;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    public String showNotice(@CurrentUser Member member, Model model) {
        List<Notice> noticeList = noticeRepository.findByMemberAndCheckedOrderByNoticeTime(member, false);

        model.addAttribute(member);
        model.addAttribute("noticeList", noticeList);
        return "notice/noticeList";
    }
}
