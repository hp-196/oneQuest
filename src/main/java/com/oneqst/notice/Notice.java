package com.oneqst.notice;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Notice {

    @Id @GeneratedValue
    @Column(name = "NOTICE_ID")
    private Long id;

    private String title; //알림 제목

    private String content; //알림 내용

    @ManyToOne
    private Member member; //알림 멤버

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType; //알림 타입

    private LocalDateTime noticeTime; //알림시간

    private boolean checked; //알림 체크 여부




}
