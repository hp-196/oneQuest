package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.user.User_Community_Type;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User_Community {
    /**
     * 유저의 커뮤니티 데이터,
     * number : 유저의 번호,
     * id : 유저의 id,
     * user_community_type : 대상 유저에 대한 타입,
     * target_id : 대상 유저의 id
     */
    @Id
    @GeneratedValue
    private Long number;
    private String id;

    @Enumerated(EnumType.STRING)
    private User_Community_Type user_community_type;
    private String target_id;
}
