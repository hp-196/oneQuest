package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.service.MyContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 나의 포스트, 댓글 조회용 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class ActivityController {

    private final MyContentService myContentService;

    @GetMapping("/activity/posts")
    public String myActivityPostLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("postList", myContentService.myActivityQuestPostLookup(member.getId()));
        return "activity/my-posts";
    }

    @GetMapping("/activity/comments")
    public String myActivityCommentLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("commentList", myContentService.myActivityCommentLookup(member.getId()));
        return "activity/my-comments";
    }

    @GetMapping("/activity/quests")
    public String myActivityQuestLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("questList", myContentService.myActivityQuestLookup(member.getId()));
        return "activity/my-quests";
    }

    @GetMapping("/activity/quest/auths")
    public String myActivityAuthPostLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("authPostList", myContentService.myActivityAuthPostLookup(member.getId()));
        return "activity/my-auths";
    }
}
