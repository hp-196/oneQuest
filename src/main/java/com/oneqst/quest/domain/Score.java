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
    @Column(name = "Score_id")
    private Long id;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private AuthPost authPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest;
}
