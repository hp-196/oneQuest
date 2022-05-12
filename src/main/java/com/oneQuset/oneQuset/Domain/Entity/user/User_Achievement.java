package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.user.Badge;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_achivement")
public class User_Achievement {
    /**
     * 유저의 성취감 관련 데이터,
     * id : 유저의 고유 id,
     * login_streak : 연속 로그인 횟수,
     * mission_streak : 연속 미션 성공 횟수,
     * mission_shared_count : 미션 공유한 횟수,
     * likes_count : 미션에 대한 좋아요를 받은 총 수,
     * badge : 유저의 뱃지
     */
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private String id;
    private int login_streak;
    private int mission_streak;
    private Long mission_shared_count;
    private Long likes_count;

    @Enumerated(EnumType.STRING)
    private Badge badge;
}
