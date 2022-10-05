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
public class MyContentController {

    private final MyContentService myContentService;

    @GetMapping("/myPosts")
    public String myPostLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("postList", myContentService.myQuestPostLookup(member.getId()));
        return "my-posts";
    }

    @GetMapping("/myComments")
    public String myCommentLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("commentList", myContentService.myCommentLookup(member.getId()));
        return "my-comments";
    }
}
