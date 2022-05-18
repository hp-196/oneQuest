package com.oneQuset.oneQuset.domain.entity.group;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 19 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Group_Community as GC
 * 그룹의 태그 정보를 담은 테이블 ( 관심사 )
 */
@Entity
@Getter
@Setter
@Table(name = "group_tag")
public class Group_Tag {
    /**
     * id : 그룹 태그의 식별자
     * group_number : 그룹의 식별 번호
     * tag : 그룹의 태그
     */
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_number")
    private Group group;

    private String tag;
}
