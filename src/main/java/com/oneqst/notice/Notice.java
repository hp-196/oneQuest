package com.oneqst.notice;

import com.oneqst.Member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Notice {

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title; //알림 제목

    private String content; //알림 내용

    private String byMember; //알림당사자 닉네임

    @ManyToOne
    private Member member; //알림을 받을 멤버

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType; //알림 타입

    private LocalDateTime noticeTime; //알림시간

    private boolean checked; //알림 체크 여부

    private String url; //알림 주소


}
