package com.oneQuset.oneQuset.domain.entity.user;

import com.oneQuset.oneQuset.domain.entity.enum_type.Type;
import lombok.*;

import javax.persistence.*;

/**
 * Create Date : [ 2022 - 05 - 18 ]
 * Last Update Date :
 * User_Community as UC
 * 유저의 커뮤니티 정보를 담은 테이블
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_community")
public class User_Community {
    /**
     * number : 생성 번호
     * user_id : 유저의 식별자
     * type : 팔로우, 팔로워의 유형
     * target_id : 대상 유저의 식별자
     */
    @Id
    @GeneratedValue
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;
    private String target_id;

}
