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

    @Id
    @GeneratedValue
    @Column(name = "AuthPost_id")
    private Long id;

    private String title; //포스트 제목

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; //포스트 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest; //퀘스트

    private String content; //포스트 내용

    private LocalDateTime postTime; //포스트 시간

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String postImage; //포스팅 이미지

    private boolean confirm; //검증 여부

    @OneToOne(mappedBy = "authPost", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Score score; //점수

    @OneToMany(mappedBy = "authPost", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public boolean isConfirm() {
        return this.confirm;
    }
}
