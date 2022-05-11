package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User_Community {
    @Id
    @GeneratedValue
    private Long number;
    private String id;
    private Enum user_community_type;
    private String target_id;
}
