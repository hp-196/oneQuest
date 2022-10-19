package com.oneqst.quest.controller;

import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.quest.domain.*;
import com.oneqst.quest.dto.*;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.repository.ScoreRepository;
import com.oneqst.quest.service.AuthService;
import com.oneqst.quest.service.CommentService;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;
    private final CommentService commentService;
    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;
    private final AuthPostRepository authPostRepository;
    private final ScoreRepository scoreRepository;
    private final AuthService authService;
    private final QuestValidator questValidator;
    private final UpdateValidator updateValidator;

    @InitBinder("questDto")
    public void initNewQuestBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(questValidator);
    }

    @InitBinder("questUpdateDto")
    public void updateQuestBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateValidator);
    }

    /**
     * 불건전한 퀘스트 접근 예외
     */
    private void extracted(Member member, Quest quest) {
        if (!quest.getQuestMember().contains(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+quest+"로 불건전한 접근 시행");
        }
        if (quest == null) {
            throw new IllegalArgumentException(member.getNickname()+"가 null인 퀘스트로 접근");
        }
    }

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
    public String newQuestPost(@CurrentUser Member member, @Valid QuestDto questDto, Errors errors, Model model) throws UnsupportedEncodingException {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            return "new-quest";
        }
        Quest newQuest = questService.newQuest(questDto, member);
        String encodedUrl = URLEncoder.encode(newQuest.getQuestUrl(), "UTF-8");
        return "redirect:/quest/" + encodedUrl;
    }

    /**
     * 퀘스트 조회
     */
    @GetMapping("/quest/{url}")
    public String questView(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest == null) {
            return "redirect:/";
        }
        List<QuestPost> questPostList = questPostRepository.findByQuestOrderByPostTimeDesc(quest);
        List<AuthPost> authPostList = authPostRepository.findByQuestOrderByPostTimeDesc(quest);
        List<Score> scoreList = scoreRepository.findByQuest(quest);
        List<Member> memberList = quest.getQuestMember();
        List<Map.Entry<Member, Integer>> score = authService.countScore(scoreList, memberList);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new InviteDto());
        model.addAttribute("questPostList", questPostList);
        model.addAttribute("authPostList", authPostList);
        model.addAttribute("scoreList", scoreList);
        model.addAttribute("scoreMap", score);
        return "quest/view";

    }

    /**
     * 퀘스트 수정
     */
    @GetMapping("/quest/{url}/update")
    public String questUpdate(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new QuestUpdateDto(quest));

        return "quest-update";
    }


    /**
     * 퀘스트 수정 POST
     */
    @PostMapping("/quest/{url}/update")
    public String questUpdatePost(@CurrentUser Member member, @PathVariable String url, @Valid QuestUpdateDto questUpdateDto, Errors errors, Model model) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            model.addAttribute(member);
            model.addAttribute(quest);
            return "quest-update";
        }
        questService.questUpdate(quest, questUpdateDto);
        String encodedUrl = URLEncoder.encode(questUpdateDto.getQuestUrl(), "UTF-8");
        return "redirect:/quest/" + encodedUrl;
    }

    /**
     * 퀘스트 참여
     */
    @GetMapping("/quest/{url}/join")
    public String joinQuest(@CurrentUser Member member, @PathVariable String url, Model model) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest.isQuestRecruitEnd() == false) {
            throw new IllegalArgumentException(member.getNickname()+"가 불건전한 참여 시도. 퀘스트 모집중이 아님");
        }
        model.addAttribute(member);
        model.addAttribute(quest);
        questService.addQuestMember(quest, member);
        return "redirect:/quest/" + quest.encodedUrl();
    }

    /**
     * 퀘스트 멤버 초대
     */
    @PostMapping("/quest/{url}/invite")
    public String inviteMember(@CurrentUser Member member, @Valid InviteDto inviteDto,
                               @PathVariable String url, Errors errors) throws UnsupportedEncodingException {
        if (errors.hasErrors()) {
            return "redirect:/quest/" + url;
        }
        Quest quest = questRepository.findByQuestUrl(url);
        questService.inviteMember(inviteDto, member, quest);
        return "redirect:/quest/" + quest.encodedUrl();
    }

    /**
     * 퀘스트 탈퇴
     */
    @GetMapping("/quest/{url}/withdraw")
    public String questWithdraw(@CurrentUser Member member, @PathVariable String url) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        questService.questWithdraw(quest, member);
        return "redirect:/quest/" + quest.encodedUrl();
    }

    /**
     * 퀘스트 포스팅 GET
     */
    @GetMapping("/quest/{url}/post")
    public String questPosting(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
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
                                   @Valid QuestPostDto questPostDto, Errors errors) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            return "quest/quest-posting";
        }
        QuestPost questPost = questService.questPost(questPostDto, quest, member);
        return "redirect:/quest/" + quest.encodedUrl() + "/post/" + questPost.getId();
    }

    /**
     * 퀘스트 포스팅 조회
     */
    @GetMapping("/quest/{url}/post/{id}")
    public String getQuestPost(@CurrentUser Member member, @PathVariable String url,
                               @PathVariable Long id, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        QuestPost questPost = questPostRepository.getById(id);
        List<Comment> commentList = commentService.findCommentAll(id);
        model.addAttribute(member);
        model.addAttribute(questRepository.findByQuestUrl(url));
        model.addAttribute(questPost);
        model.addAttribute(new CommentDto());
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
        extracted(member, quest);
        QuestPost questPost = questPostRepository.getById(id);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(questPost);
        model.addAttribute(new QuestPostUpdateDto(questPost));
        return "quest/post-update";
    }

    /**
     * 퀘스트 포스팅 수정 POST
     */
    @PostMapping("/quest/{url}/post/{id}/update")
    public String updateQuestPosting(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id,
                                     @Valid QuestPostUpdateDto questPostUpdateDto, Errors errors) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        QuestPost questPost = questPostRepository.getById(id);
        if (errors.hasErrors()) {
            return "redirect:/quest/" + quest.encodedUrl() + "/post/" + id;
        }
        if (questPost.getWriter().equals(member)) {
            questService.updateQuestPost(questPost, questPostUpdateDto);
            return "redirect:/quest/" + quest.encodedUrl() + "/post/" + id;
        }
        return "redirect:/quest/" + quest.encodedUrl() + "/post/" + id;
    }

    /**
     * 퀘스트 포스팅 삭제
     */
    @GetMapping("/quest/{url}/post/{id}/delete")
    public String deleteQuestPost(@CurrentUser Member member, @PathVariable String url, @PathVariable Long id) throws UnsupportedEncodingException {
        QuestPost questPost = questPostRepository.getById(id);
        if (!questPost.getWriter().equals(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+questPost+"로 불건전한 삭제 접근 시행");
        }
        questService.deleteQuestPost(questPost);
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        return "redirect:/quest/" + encodedUrl;
    }

    /**
     * 퀘스트 공지사항 포스팅
     */
    @GetMapping("/quest/{url}/post/notice")
    public String questNotice(@CurrentUser Member member, @PathVariable String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        extracted(member, quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute(new QuestPostDto());
        return "quest/notice-posting";
    }

    /**
     * 퀘스트 공지사항 포스팅 POST
     */
    @PostMapping("/quest/{url}/post/notice")
    public String questNoticePost(@CurrentUser Member member, @PathVariable String url,
                                  @Valid QuestPostDto questPostDto, Errors errors) throws UnsupportedEncodingException {
        Quest quest = questRepository.findByQuestUrl(url);
        if (errors.hasErrors()) {
            log.info("퀘스트 포스팅 실패");
            return "quest/quest-posting";
        }
        QuestPost questPost = questService.questNoticePost(questPostDto, quest, member);
        return "redirect:/quest/" + quest.encodedUrl() + "/post/" + questPost.getId();
    }

    /**
     * 해당 유저가 관리자인 퀘스트를 조회
     * @param member    유저
     * @param model 퀘스트 목록
     * @return  master.html
     */
    @GetMapping("/quest/master")
    public String questMasterLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("questList", questRepository.questMaster(member.getId()));
        return "quest/master";
    }

    /**
     * 해당 유저가 참여중인 퀘스트를 조회
     * @param member    유저
     * @param model 퀘스트 목록
     * @return  member.html
     */
    @GetMapping("/quest/member")
    public String questMemberLookup(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute("questList", questRepository.questMember(member.getId()));
        return "quest/member";
    }
}
