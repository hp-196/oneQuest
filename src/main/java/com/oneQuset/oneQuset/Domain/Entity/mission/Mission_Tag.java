package com.oneQuset.oneQuset.domain.entity.mission;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Mission_Tag as MT
 * 미션의 태그 정보를 담은 테이블 ( 관심사 )
 */
@Entity
@Getter
@Setter
@Table(name = "mission_tag")
public class Mission_Tag {
    /**
     * number : 미션 태그의  식별 번호
     * mission_id : 미션의 식별자
     * tag : 미션의 태그명
     */
    @Id
    @GeneratedValue
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private String tag;
}
