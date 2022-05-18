package com.oneQuset.oneQuset.domain.entity.mission;

import com.oneQuset.oneQuset.domain.entity.enum_type.Mission_Create_User_Type;
import com.oneQuset.oneQuset.domain.entity.enum_type.Mission_User_Role;
import com.oneQuset.oneQuset.domain.entity.group.Group;
import com.oneQuset.oneQuset.domain.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Mission_Community as MC
 * 미션의 커뮤니티 정보를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "mission_community")
public class Mission_Community {
    /**
     * number : 미션 커뮤니티의 식별 번호
     * user_id : 유저의 식별자
     * mission_title : 미션의 타이틀
     * group_number : 그룹의 식별 번호
     * MUR : 미션에서의 유저의 역활
     * MCUT : 미션을 생성한 유저의 타입
     */
    @Id
    @GeneratedValue
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_title")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_number")
    private Group group;

    @OneToOne(mappedBy = "mission_community",fetch = FetchType.LAZY)
    private Mission_Option mission_option;

    @Enumerated(EnumType.STRING)
    private Mission_User_Role MUR;

    @Enumerated(EnumType.STRING)
    private Mission_Create_User_Type MCUT;
}
