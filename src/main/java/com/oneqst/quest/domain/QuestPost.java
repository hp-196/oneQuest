package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
public class QuestPost {
    
    @Id @GeneratedValue
    private Long id;
    
    private String title; //포스트 제목

    @ManyToOne
    private Member writer; //포스트 작성자

    @ManyToOne
    private Quest quest; //퀘스트

    private String content; //포스트 내용

    private LocalDateTime postTime; //포스트 시간

    private boolean notice; //공지사항 여부

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String postImage; //포스팅 이미지

    @OneToMany(mappedBy = "post")
    private List<QuestComment> questCommentList = new ArrayList<>();


    /*********************** 연관관계 편의 메소드 **********************/

    public void addCommentList(QuestComment questComment) {
        this.questCommentList.add(questComment);
    }

    public void removeComment(QuestComment questComment) {
        this.questCommentList.remove(questComment);
    }
}
