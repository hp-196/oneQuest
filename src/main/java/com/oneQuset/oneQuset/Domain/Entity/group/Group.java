package com.oneQuset.oneQuset.domain.entity.group;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date : [ 2022 - 05 - 19 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Group as G
 * 그룹 데이터를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "group")
public class Group {
    /**
     * number : 그룹의 식별 번호
     * name : 그룹의 이름
     * image : 그룹의 대표 이미지
     * notice : 그룹의 공지
     * create_date : 그룹의 생성 날짜
     * update_date : 그룹의 수정 날짜
     */
    @Id
    @GeneratedValue
    private Long number;

    @OneToMany(mappedBy = "group")
    private List<Group_Community> group_community_list = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Group_Tag> group_tag_list = new ArrayList<>();

    private String name;
    private String image;
    private String notice;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

}
