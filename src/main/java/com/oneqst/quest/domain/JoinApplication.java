package com.oneqst.quest.domain;

import com.oneqst.Member.domain.Member;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinApplication {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Quest quest; //신청한 퀘스트

    @ManyToOne
    private Member member; //신청한 회원

    private LocalDateTime time; //신청 시간

    private boolean accepted; //수락 여부

}
