package com.oneQuset.oneQuset.domain.entity.mission;

import com.oneQuset.oneQuset.domain.entity.enum_type.Mission_Create_User_Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date : [ 2022 - 05 - 18 ]
 * Mission as M
 * 미션의 정보를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "mission")
public class Mission {
    /**
     * id : 미션의 식별자
     * Mission_Create_User_Type : 미션의 생성 유저 타입
     * EX) 일반 유저, 그룹
     * title : 미션의 제목
     * context : 미션의 내용
     * likes : 미션의 좋아요 수
     * shard_count : 미션의 공유된 수
     * create_date : 미션의 생성 날짜
     * update_date : 미션의 수정 날짜
     * target_date : 미션의 목표 날짜
     */
    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Mission_Create_User_Type MCUT;

    @OneToMany(mappedBy = "mission")
    private List<Mission_Tag> mission_tag_list;

    @OneToMany(mappedBy = "mission")
    private List<Mission_Community> mission_community_list;

    private String title;
    private String context;
    private Long Likes;
    private Long shard_count;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private LocalDateTime target_date;
}
