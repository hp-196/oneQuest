package com.oneqst.Member.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickname; //닉네임

    private String password; //비번

    @Column(unique = true)
    private String email; //이메일

    private boolean emailAuth; //이메일 인증 여부

    private String emailToken; //이메일 인증 토큰값

    private LocalDateTime signUpTime; //회원가입 시간

    private String introduce; //자기소개

    private String profileImage; //프로필 이미지

    private String job; //직업

    private String url; //sns 주소

    private String address; //집 주소

    private boolean emailAlarm; //이메일 알람 여부

    private boolean webAlarm; //웹 알람 여부


    public void EmailTokenCreate() {
        this.emailToken = UUID.randomUUID().toString();
    }

}
