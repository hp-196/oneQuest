package com.oneqst.Member.controller;

import com.oneqst.Member.dto.MemberDto;
import com.oneqst.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class MemberValidator implements Validator {

    //https://dev-coco.tistory.com/125 참고

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> target) {
        return target.isAssignableFrom(MemberDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;

        if (memberRepository.existsByNickname(memberDto.getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임입니다");
        }
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일입니다");
        }

    }
}
