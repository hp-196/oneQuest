package com.oneQuset.oneQuset.domain.entity.user;

import com.oneQuset.oneQuset.domain.entity.enum_type.Badge_Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 19 ]
 * Last Update Date : [ 2022 - 05 - 19 ]
 * Badge as B
 * 뱃지의 정보를 담은 테이블
 */
@Entity
@Getter
@Setter
@Table(name = "badge")
public class Badge {
    /**
     * id : 뱃지의 식별자
     * badge : 뱃지 이름
     * image : 뱃지 이미지
     * target : 뱃지의 타입
     * goal : 뱃지의 목표
     */
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "badge")
    private User_Achievement user_achievement;
    private String image;

    @Enumerated(EnumType.STRING)
    private Badge_Type target;
    private Long goal;
}
