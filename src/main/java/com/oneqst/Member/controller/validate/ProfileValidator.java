package com.oneqst.Member.controller.validate;

import com.oneqst.Member.domain.Profile;
import com.oneqst.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProfileValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Profile.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Profile profile = (Profile) target;

        if (!profile.getCurrentNickname().equals(profile.getNickname()) && memberRepository.existsByNickname(profile.getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임입니다.");
        }

        if (!profile.getCurrentEmail().equals(profile.getEmail()) && memberRepository.existsByEmail(profile.getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일입니다.");
        }


    }
}
