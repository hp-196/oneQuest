package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private String id;
    private String nickname;
    private String profile_image; // CLOB
    private Enum Role;

}
