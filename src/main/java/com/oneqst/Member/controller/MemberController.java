package com.oneqst.Member.controller;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.domain.Password;
import com.oneqst.Member.domain.Profile;
import com.oneqst.Member.dto.MemberDto;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;


    /**
     * 로그인 페이지
     */
     @GetMapping("/login")
     public String login() {
         return "login";
     }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/sign-up")
    public String signUpGet(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "sign-up";
    }

    /**
     * 회원가입 포스트
     */
    @PostMapping("/sign-up")
    public String signUpPost(@Valid MemberDto memberDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            return "sign-up";
        }
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            log.info("이미 있는 이메일");
            return "sign-up";
        }
        if (memberRepository.existsByNickname(memberDto.getNickname())) {
            log.info("이미 있는 닉네임");
            return "sign-up";
        }

        Member newMember = memberService.newMember(memberDto);
        memberService.sendMail(newMember);

        return "redirect:/";
    }


    /**
     * 이메일 인증
     */
    @GetMapping("/email-auth")
    public String emailAuth(String email, String token, Model model) {
        Member member = memberRepository.findByEmail(email);
        if (member == null || !member.getEmailToken().equals(token)) {
            model.addAttribute("error", "wrong.email");
            return "email-auth";
        }
        memberService.setEmailAuthAndTime(member);
        model.addAttribute("nickname", member.getNickname());
        return "email-auth";
    }

    /**
     * 프로필 뷰
     */
    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser Member member) {
        Member profileMember = memberRepository.findByNickname(nickname);
        if (profileMember == null) {
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다.");
        }
        if (profileMember.equals(member)) {
            model.addAttribute(new Profile(profileMember));
            model.addAttribute(new Password());
            model.addAttribute("isOwner", profileMember);
        }
        model.addAttribute("member", profileMember);
        return "profile";
    }

    /**
     * 프로필 수정
     * @return
     */
    @PostMapping("/profile/update")
    public String updateProfile(@CurrentUser Member member, @Valid Profile profile, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            log.info(String.valueOf(errors));
            return "redirect:/profile/" + member.getNickname();
        }
        memberService.updateProfile(member, profile);
        return "redirect:/profile/" + member.getNickname();
    }

    /**
     * 패스워드 업데이트
     */
    @PostMapping("/profile/update/password")
    public String updatePassword(@CurrentUser Member member, @Valid Password password, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            log.info(String.valueOf(errors));
            return "redirect:/profile/" + member.getNickname();
        }
        if (memberService.updatePassword(member, password)) {
            log.info("비밀번호 변경 성공");
            return "redirect:/profile/" + member.getNickname();
        }
        return "redirect:/profile/" + member.getNickname();
    }

}
