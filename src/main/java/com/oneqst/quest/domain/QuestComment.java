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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestComment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Member writer; //댓글 작성자

    @ManyToOne
    private QuestPost post; //포스트

    private String content; //댓글 내용

    private LocalDateTime postTime; //댓글 쓴 시간




}
