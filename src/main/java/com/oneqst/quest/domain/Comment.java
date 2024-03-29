package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

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

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; //댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestPost post; //커뮤니티 게시판포스트

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthPost authPost; //인증 게시판 포스트

    @NotBlank
    private String content; //댓글 내용

    private LocalDateTime postTime; //댓글 쓴 시간


}
