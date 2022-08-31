package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer; //댓글 작성자

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestPost post; //포스트

    private String content; //댓글 내용

    private LocalDateTime postTime; //댓글 쓴 시간




}
