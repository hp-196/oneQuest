package com.oneqst.Member.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profile {

    private String nickname;

    private String introduce;

    private String job;

    private String address;

    private String email;

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
    }
}
