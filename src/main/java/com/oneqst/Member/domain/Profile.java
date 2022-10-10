package com.oneqst.Member.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Length(min = 2, max = 30)
    @Pattern(regexp = "[0-9a-zA-Z가-힣]{2,50}", message = "지원하지않는 형식입니다.")
    private String nickname;

    private String currentNickname;

    private String introduce;

    private String job;

    private String address;

    @Email
    private String email;

    private String currentEmail;

    private String url;

    private String profileImage;

    public Profile(Member member) {
        this.nickname = member.getNickname();
        this.introduce = member.getIntroduce();
        this.job = member.getJob();
        this.address = member.getAddress();
        this.email = member.getEmail();
        this.url = member.getUrl();
        this.profileImage = member.getProfileImage();
        this.currentNickname = member.getNickname();
        this.currentEmail = member.getEmail();
    }
}
