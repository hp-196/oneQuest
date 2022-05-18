package com.oneQuset.oneQuset.domain.entity.group;

import com.oneQuset.oneQuset.domain.entity.enum_type.Role;
import com.oneQuset.oneQuset.domain.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Create Date : [ 2022 - 05 - 19 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Group_Community as GC
 * 그룹 커뮤니티의 데이터를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "group_community")
public class Group_Community {
    /**
     * number : 그룹 커뮤니티의 식별 번호
     * user_id : 유저의 식별자
     * group_number : 그룹의 식별 번호
     * join_date : 그룹 가입 시간
     * role : 그룹에서의 유저의 역활
     */
    @Id
    @GeneratedValue
    private Long number;

    @OneToOne(mappedBy = "group_community")
    private Group_Option group_option;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_number")
    private Group group;

    private LocalDateTime join_date;
    private Role role;
}
