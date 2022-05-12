package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tag")
public class User_Tag {
    /**
     * 유저의 관심사를 나태내는 태그,
     * id : 유저의 id,
     * tag : 유저의 태그
     */
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private String id;
    private String tag;
}
