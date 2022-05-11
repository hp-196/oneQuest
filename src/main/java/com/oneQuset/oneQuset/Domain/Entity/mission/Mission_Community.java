package com.oneQuset.oneQuset.Domain.Entity.mission;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Role;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mission_community")
public class Mission_Community {
    /**
     * 미션 커뮤니티 데이터,
     * number : 미션 번호,
     * id : 유저의 id,
     * role : 해당 유저의 역활
     */
    @Id
    @GeneratedValue
    private Long number;
    private String id;  // user_id
    @Enumerated(EnumType.STRING)
    private Role role;
}
