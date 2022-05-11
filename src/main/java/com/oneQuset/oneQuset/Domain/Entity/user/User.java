package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Role;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 유저 데이터,
     * id : 유저의 고유 id,
     * nickname : 유저의 닉네임,
     * profile_image : 유저의 프로필 이미지,
     * role : 유저의 역활
     */
    @Id
    @GeneratedValue
    private String id;
    private String nickname;
    private String profile_image; // CLOB

    @Enumerated(EnumType.STRING)
    private Role role;

}
