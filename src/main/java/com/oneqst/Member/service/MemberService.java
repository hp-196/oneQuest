package com.oneqst.Member.service;

import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.Member.domain.Member;
import com.oneqst.Member.domain.Password;
import com.oneqst.Member.domain.Profile;
import com.oneqst.Member.dto.MemberDto;
import com.oneqst.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일 전송
     * https://velog.io/@jjangchen/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EB%A9%94%EC%9D%BC%EB%B0%9C%EC%86%A1 참고
     */
    public void sendMail(Member newMember) {
        newMember.EmailTokenCreate();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(newMember.getEmail());
        simpleMailMessage.setSubject("원퀘스트 메일");
        simpleMailMessage.setText("/email-auth?token="+ newMember.getEmailToken() + "&email=" + newMember.getEmail());
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 회원 가입
     */
    public Member newMember(MemberDto memberDto) {
        Member member = Member.builder()
                .nickname(memberDto.getNickname())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .address(memberDto.getAddress())
                .emailAlarm(true)
                .webAlarm(true)
                .build();

        Member newMember = memberRepository.save(member);
        return newMember;
    }

    /**
     * 로그인
     * https://velog.io/@leyuri/%ED%98%84%EC%9E%AC-%EC%9D%B8%EC%A6%9D%EB%90%9C-%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EB%B3%B4-%EC%B0%B8%EC%A1%B0 참고
     */
    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new MemberInfo(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(token);

    }

    /**
     * 회원정보 DB 체크여부
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(nickname);
        if (member == null) {
            throw new UsernameNotFoundException(nickname);
        }
        log.info("Success find member {}", member);
        return new MemberInfo(member);
    }

    /**
     * @Transactional 이슈발생으로 인하여 생성 (@Transactional범위 안에 없으면 DB에 반영이 안됨)
     */
    public void setEmailAuthAndTime(Member member) {
        member.setEmailAuth(true);
        member.setSignUpTime(LocalDateTime.now());
    }

    /**
     * 프로필 업데이트, 06-06 기준 이메일 닉네임 백엔드 검증, 수정시 알림메시지 출력 미완성
     */
    public void updateProfile(Member member, Profile profile) {

        member.setEmail(profile.getEmail());
        member.setNickname(profile.getNickname());
        member.setAddress(profile.getAddress());
        member.setIntroduce(profile.getIntroduce());
        member.setJob(profile.getJob());
        member.setUrl(profile.getUrl());
        member.setProfileImage(profile.getProfileImage());
        memberRepository.save(member);

    }

    /**
     * 비밀번호 수정
     */
    public boolean updatePassword(Member member, Password password) {
        Member findMember = memberRepository.findByNickname(member.getNickname());
        if(!passwordEncoder.matches(password.getCurrentPassword(), findMember.getPassword())) {
            log.info("현재 비밀번호 매치 실패");
            return false;
        }
        if (!password.getNewPassword().equals(password.getReNewPassword())) {
            log.info(password.getNewPassword());
            log.info(password.getReNewPassword());
            log.info("비밀번호 재입력 매치 실패");
            return false;
        }
        member.setPassword(passwordEncoder.encode(password.getNewPassword()));
        memberRepository.save(member);
        return true;
    }


    public void updateProfileImage(Profile profile) {
        Member member = memberRepository.findByNickname(profile.getNickname());
        member.setProfileImage(profile.getProfileImage());
        memberRepository.save(member);
    }
}