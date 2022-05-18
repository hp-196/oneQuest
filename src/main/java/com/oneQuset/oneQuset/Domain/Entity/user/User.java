package com.oneQuset.oneQuset.domain.entity.user;

import com.oneQuset.oneQuset.domain.entity.enum_type.Provider;
import com.oneQuset.oneQuset.domain.entity.enum_type.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date :
 * User as U
 * 유저의 정보를 담은 테이블
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    /**
     * [ Auth ]
     * number : 유저의 생성 순서 번호
     * id : 유저의 식별자
     * name : 유저의 이름
     * email : 유저의 이메일
     * provider : 유저의 소셜 공급자
     */
    @Id
    @GeneratedValue
    private Long number;
    private String id;

    @OneToMany(mappedBy = "user")
    private List<User_Tag> user_tag_list = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<User_Community> user_community_list = new ArrayList<>();
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    /**
     * [ Time ]
     * create_date : 생성 날짜
     * update_date : 프로필 수정 날짜
     */
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    /**
     * [ Profile ]
     * image : 유저의 대표 사진
     * nick_name : 유저의 닉네임
     * role : 유저의 역활
     */
    private String image;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;
}
