package com.oneQuset.oneQuset.domain.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * User_Achievement as UA
 * 유저의 성취감 데이터를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "user_achievement")
public class User_Achievement {
    /**
     *
     */
    @Id
    @GeneratedValue
    private Long Number;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Long login_streak;
    private Long mission_count;
    private Long mission_shared_count;
    private Long likes_count;

    @OneToMany(mappedBy = "user_achievement")
    List<Badge> badge_list = new ArrayList<>();


}
