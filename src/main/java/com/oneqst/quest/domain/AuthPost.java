package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPost {

    @Id @GeneratedValue
    private Long id;

    private String title; //포스트 제목

    @ManyToOne
    private Member writer; //포스트 작성자

    @ManyToOne
    private Quest quest; //퀘스트

    private String content; //포스트 내용

    private LocalDateTime postTime; //포스트 시간

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String postImage; //포스팅 이미지

    private boolean confirm; //검증 여부

    @OneToOne(mappedBy = "authPost")
    private Score score; //점수

    @OneToMany(mappedBy = "authPost")
    private List<Comment> commentList = new ArrayList<>();
}
