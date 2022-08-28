package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestComment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.*;
import com.oneqst.quest.repository.QuestCommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;
    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;

    /**
     * 퀘스트 생성 페이지
     */
    @GetMapping("/new-quest")
    public String newQuest(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute(new QuestDto());
        return "new-quest";
    }

    /**
     * 퀘스트 생성 POST
     */
    @PostMapping("/new-quest")
    public String newQuestPost(@CurrentUser Member member, @Valid QuestDto questDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            return "new-quest";
        }
        Member newMember = member;
        Quest newQuest = questService.newQuest(questDto, newMember);
        return "redirect:/quest/" + newQuest.getQuestUrl();
    }

    /**
     * 퀘스트 조회
     */
    @GetMapping("/quest/{url}")
    public String questView(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        List<QuestPost> questPostList = questPostRepository.findByQuest(quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute("questPostList", questPostList);
        log.info(member.getPostList().toString());

        return "quest/view";

    }

    /**
     * 퀘스트 수정
     */
    @GetMapping("/quest/{url}/update")
    public String questUpdate(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest == null) {
            throw new IllegalArgumentException("스터디 없음");
        }
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new QuestUpdateDto(quest));

        return "quest-update";
    }

    /**
     * 퀘스트 수정
     * th:action="@{update}" 만해도 되는데 왜이러는지 모르겠음
     */
    @PostMapping("/quest/{url}/update")
    public String questUpdatePost(@CurrentUser Member member, @PathVariable String url, @Valid QuestUpdateDto questUpdateDto, Errors errors, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            model.addAttribute(member);
            model.addAttribute(quest);
            log.info("퀘스트 수정 에러");
            return "quest-update";
        }
        questService.questUpdate(quest, questUpdateDto);
        log.info("퀘스트 수정 완료");
        return "redirect:/quest/" + quest.getQuestUrl();
    }

    /**
     * 퀘스트 참여
     */
    @GetMapping("/quest/{url}/join")
    public String joinQuest(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        model.addAttribute(member);
        model.addAttribute(quest);
        questService.addQuestMember(quest, member);
        return "redirect:/quest/" + quest.getQuestUrl();
    }

    /**
     * 퀘스트 탈퇴
     */
    @GetMapping("/quest/{url}/withdraw")
    public String questWithdraw(@CurrentUser Member member, @PathVariable String url) {
        Quest quest = questRepository.findByQuestUrl(url);
        questService.questWithdraw(quest, member);
        return "redirect:/quest/" + quest.getQuestUrl();
    }

    /**
     * 퀘스트 포스팅 GET
     */
    @GetMapping("/quest/{url}/post")
    public String questPosting(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest == null) {
            throw new IllegalArgumentException("스터디 없음");
        }
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new QuestPostDto());
        return "quest/quest-posting";
    }

    /**
     * 퀘스트 포스팅 POST
     */
    @PostMapping("/quest/{url}/post")
    public String questPostingPost(@CurrentUser Member member, @PathVariable String url,
                                   @Valid QuestPostDto questPostDto, Errors errors) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            log.info("퀘스트 포스팅 실패");
            return "quest/quest-posting";
        }
        QuestPost questPost = questService.questPost(questPostDto, quest, member);
        return "redirect:/quest/" + quest.getQuestUrl() + "/post/" + questPost.getId();
    }

    /**
     * 퀘스트 포스팅 조회
     */
    @GetMapping("/quest/{url}/post/{id}")
    public String getQuestPost(@CurrentUser Member member, @PathVariable String url,
                               @PathVariable Long id, Model model) {
        QuestPost questPost = questPostRepository.getOne(id);
        List<QuestComment> commentList = questService.findCommentAll(questPost, id);
        model.addAttribute(member);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute(questPost);
        model.addAttribute("commentList", commentList);
        return "quest/post-view";
    }

    /**
     * 퀘스트 포스팅 수정
     */
    @GetMapping("/quest/{url}/post/{id}/update")
    public String updateQuestPost(@CurrentUser Member member, @PathVariable String url,
                                  @PathVariable Long id, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        QuestPost questPost = questPostRepository.getOne(id);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(questPost);
        model.addAttribute(new QuestPostUpdateDto(questPost));
        return "quest/post-update";
    }

    /**
     * 퀘스트 포스팅 수정
     */
    @PostMapping("/quest/{url}/post/{id}/update")
    public String updateQuestPosting(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                                     @Valid QuestPostUpdateDto questPostUpdateDto, Errors errors) {
        QuestPost questPost = questPostRepository.getOne(id);
        if (errors.hasErrors()) {
            log.info("퀘스트 포스트 수정 매개변수 에러");
            return "redirect:/quest/" + url + "/post/" + id;
        }
        log.info(questPost.getWriter().getNickname());
        if (questPost.getWriter().getNickname().equals(member.getNickname())) {
            questService.updateQuestPost(questPost, questPostUpdateDto);
            return "redirect:/quest/" + url + "/post/" + id;
        }
        log.info("퀘스트 포스트 수정 에러");
        return "redirect:/quest/" + url + "/post/" + id;
    }

    /**
     * 퀘스트 포스팅 삭제
     */
    @DeleteMapping("/quest/{url}/post/{id}")
    public String deleteQuestPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id) {
        QuestPost questPost = questPostRepository.getOne(id);
        questService.deleteQuestPost(questPost);
        //member.deletePost(questPost);
        log.info("포스트 삭제 성공");
        log.info(member.getPostList().toString());
        return "redirect:/quest/" + url;
    }

    /**
     * 퀘스트 공지사항 포스팅
     */
    @GetMapping("/quest/{url}/post/notice")
    public String questNotice(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest == null) {
            throw new IllegalArgumentException("스터디 없음");
        }
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new QuestPostDto());
        return "quest/notice-posting";
    }

    /**
     * 퀘스트 공지사항 포스팅
     */
    @PostMapping("/quest/{url}/post/notice")
    public String questNoticePost(@CurrentUser Member member, @PathVariable String url,
                                   @Valid QuestPostDto questPostDto, Errors errors) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            log.info("퀘스트 포스팅 실패");
            return "quest/quest-posting";
        }
        QuestPost questPost = questService.questNoticePost(questPostDto, quest, member);
        return "redirect:/quest/" + quest.getQuestUrl() + "/post/" + questPost.getId();
    }





}