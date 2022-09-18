package com.oneqst.community;

import com.oneqst.Member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CommunityJoin {

    @Id
    @GeneratedValue
    @Column(name = "CommunityJoin_id")
    private Long id;

    @ManyToOne
    private Member JoinMember; //만든 멤버

    @ManyToOne
    private Community community; //커뮤니티 매핑

    private LocalDateTime joinTime; //참여 시간

    private boolean joinVerify; //참여 여부

    private boolean attainment; //달성 여부

}
