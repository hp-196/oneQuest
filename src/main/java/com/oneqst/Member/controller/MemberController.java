package com.oneqst.Member.controller;

import com.oneqst.Member.controller.validate.MemberValidator;
import com.oneqst.Member.controller.validate.ProfileValidator;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.domain.Password;
import com.oneqst.Member.domain.Profile;
import com.oneqst.Member.dto.MemberDto;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.Member.service.MemberService;
import com.oneqst.config.AlertMessage;
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
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final ProfileValidator profileValidator;

    @InitBinder("memberDto")
    public void memberDtoBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberValidator);
    }

    @InitBinder("profile")
    public void profileBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(profileValidator);
    }


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
    public String signUpPost(@Valid MemberDto memberDto, Errors errors) throws MessagingException {
        if (errors.hasErrors()) {
            return "sign-up";
        }
        Member newMember = memberService.newMember(memberDto);
        memberService.sendMail(newMember);
        return "login";
    }


    /**
     * 이메일 인증
     */
    @GetMapping("/email-auth")
    public String emailAuth(@CurrentUser Member loginMember, String email, String token, Model model) {
        Member member = memberRepository.findByEmail(email);
        if (!loginMember.equals(member) || member == null || !member.getEmailToken().equals(token)) {
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
        model.addAttribute("member", member);
        model.addAttribute("profileMember", profileMember);
        return "profile";
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/profile/update")
    ModelAndView updateProfile(@CurrentUser Member member, @Valid Profile profile, Errors errors, ModelAndView mav) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getCode();
            mav.addObject("data", new AlertMessage(message, "/profile/"+member.getNickname()));
            mav.setViewName("alertMessage");
            return mav;
        }
        memberService.updateProfile(member, profile);
        mav.addObject("data", new AlertMessage("프로필 수정이 완료되었습니다.", "/profile/"+member.getNickname()));
        mav.setViewName("alertMessage");
        return mav;
    }


    /**
     * 패스워드 업데이트
     */
    @PostMapping("/profile/update/password")
    public ModelAndView updatePassword(@CurrentUser Member member, @Valid Password password, Errors errors, ModelAndView mav) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            mav.addObject("data", new AlertMessage(message, "/profile/"+member.getNickname()));
            mav.setViewName("alertMessage");
            return mav;
        }
        if (memberService.updatePassword(member, password)) {
            mav.addObject("data", new AlertMessage("비밀번호 수정이 완료되었습니다.", "/profile/"+member.getNickname()));
        } else {
            mav.addObject("data", new AlertMessage("비밀번호 수정이 실패하였습니다.", "/profile/"+member.getNickname()));
        }
        mav.setViewName("alertMessage");
        return mav;
    }


    @PostMapping("/profile/update/profileImage")
    public String updateProfileImage(@CurrentUser Member member, @Valid Profile profile, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            log.info(String.valueOf(errors));
            return "redirect:/profile/" + member.getNickname();
        }
        memberService.updateProfileImage(profile);
        return "redirect:/profile/" + member.getNickname();
    }
}
