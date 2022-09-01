package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @ManyToOne
    private Member writer; //댓글 작성자

    @ManyToOne
    private QuestPost post; //포스트

    @NotBlank
    private String content; //댓글 내용

    private LocalDateTime postTime; //댓글 쓴 시간




}
