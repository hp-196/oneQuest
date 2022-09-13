package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    @Id
    @GeneratedValue
    @Column(name = "score_id")
    private Long id;

    private int score;

    @ManyToOne
    private Member member;

    @OneToOne
    private AuthPost authPost;

    @ManyToOne
    private Quest quest;
}
