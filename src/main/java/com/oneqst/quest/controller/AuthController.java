package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.dto.AuthPostUpdateDto;
import com.oneqst.quest.dto.CommentDto;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.AuthService;
import com.oneqst.quest.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final QuestRepository questRepository;
    private final AuthPostRepository authPostRepository;
    private final AuthService authService;
    private final CommentService commentService;

    /**
     * 불건전한 접근 예외
     */
    private void extracted(Member member, Quest quest) {
        if (!quest.getQuestMember().contains(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 인증 접근");
        }
    }
    /**
     * 퀘스트 인증 포스팅 GET
     */
    @GetMapping("/quest/{url}/auth/post")
    public String questAuth(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new AuthPostDto());
        return "quest/auth-posting";
    }

    /**
     * 퀘스트 인증 포스팅 POST
     */
    @PostMapping("/quest/{url}/auth/post")
    public String questAuthPost(@CurrentUser Member member, @PathVariable String url,
                                @Valid AuthPostDto authPostDto) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        AuthPost authPost = authService.AuthPost(authPostDto, quest, member);
        return "redirect:/quest/" + quest.encodedUrl() + "/auth/post/" + authPost.getId();
    }

    /**
     * 퀘스트 인증 페이지 조회
     */
    @GetMapping("/quest/{url}/auth/post/{id}")
    public String getAuthPost(@CurrentUser Member member, @PathVariable String url,
                              @PathVariable Long id, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        AuthPost authPost = authPostRepository.getById(id);
        List<Comment> commentList = commentService.findCommentAuth(authPost);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute("commentList", commentList);
        model.addAttribute(authPost);
        model.addAttribute(member);
        model.addAttribute(new CommentDto());
        return "quest/auth-view";
    }

    /**
     * 퀘스트 인증 페이지 수정
     */
    @GetMapping("/quest/{url}/auth/post/{id}/update")
    public String updateAuthPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        AuthPost authPost = authPostRepository.getById(id);
        model.addAttribute(member);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute(authPost);
        model.addAttribute(new AuthPostUpdateDto(authPost));
        return "quest/auth-update";
    }

    /**
     * 퀘스트 인증 페이지 수정
     */
    @PostMapping("/quest/{url}/auth/post/{id}/update")
    public String updateAuthPosting(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                                    @Valid AuthPostUpdateDto authPostUpdateDto) throws UnsupportedEncodingException {
        AuthPost authPost = authPostRepository.getById(id);
        if (member.equals(authPost.getWriter())) {
            authService.updateAuthPost(authPost, authPostUpdateDto);
        }
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/" + encodedUrl + "/auth/post/" + id;
    }

    /**
     * 퀘스트 인증 페이지 삭제
     */
    @GetMapping("/quest/{url}/auth/post/{id}/delete")
    public String deleteAuthPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id) throws UnsupportedEncodingException {
        AuthPost authPost = authPostRepository.getById(id);
        if (member.equals(authPost.getWriter())) {
            authService.deletePost(authPost);
        }
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/" + encodedUrl;
    }


}
