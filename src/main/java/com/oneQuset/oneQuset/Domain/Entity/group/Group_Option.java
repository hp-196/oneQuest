package com.oneQuset.oneQuset.domain.entity.group;

import com.oneQuset.oneQuset.domain.entity.enum_type.Color;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 19 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Group_Option as GO
 * 그룹의 옵션을 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "group_option")
public class Group_Option {
    /**
     * number : 그룹 옵션의 식별 번호
     * group_community_number : 그룹 커뮤니티의 식별 번호
     * color : 그룹의 색상
     */
    @Id
    @GeneratedValue
    private Long number;

    @OneToOne
    @JoinColumn(name = "group_community_number")
    private Group_Community group_community;

    private Color color;
}
