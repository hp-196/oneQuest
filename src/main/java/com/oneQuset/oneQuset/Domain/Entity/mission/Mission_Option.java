package com.oneQuset.oneQuset.domain.entity.mission;

import com.oneQuset.oneQuset.domain.entity.enum_type.Color;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Mission_Option as MO
 * 미션의 옵션을 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "mission_option")
public class Mission_Option {
    /**
     * number : 미션 옵션의 식별 번호
     * mission_community_number : 미션 커뮤니티의 식별자
     * color : 미션의 색상
     */
    @Id
    @GeneratedValue
    private Long number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_community_number")
    private Mission_Community mission_community;

    @Enumerated(EnumType.STRING)
    private Color color;
}
