package com.oneQuset.oneQuset.domain.entity.user;

import lombok.*;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date :
 * User_Tag as UT
 * 유저의 태그 정보를 담은 테이블 ( 관심사 )
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tag")
public class User_Tag {
    /**
     * number : user_tag 의 생성 번호
     * user_id : 유저의 식별자
     * tag : 유저의 태그
     */
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    private String tag;
}
