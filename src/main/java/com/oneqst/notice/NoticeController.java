package com.oneqst.notice;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
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
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String showNotice(@CurrentUser Member member, Model model) {
        List<Notice> allNotice = noticeRepository.findByMemberOrderByNoticeTimeDesc(member);
        model.addAttribute(member);
        model.addAttribute("allNotice", allNotice);
        model.addAttribute("newNoticeCount", noticeRepository.countByCheckedAndMember(false, member));
        return "notice/noticeList";
    }

    @GetMapping("/notice/{id}/delete")
    public String deleteNotice(@PathVariable Long id) {
        Notice notice = noticeRepository.getById(id);
        noticeRepository.delete(notice);
        return "redirect:/notice";
    }

    @GetMapping("/notice/delete")
    public String deleteAllNotice(@CurrentUser Member member) {
        noticeService.deleteTotalNotice(member.getId());
        return "redirect:/notice";
    }

    @GetMapping("/notice/read/{id}")
    public String readOneNotice(@PathVariable Long id) {
        Notice notice = noticeRepository.getById(id);
        noticeService.readOne(notice);
        return "redirect:" + notice.getUrl();
    }
}
