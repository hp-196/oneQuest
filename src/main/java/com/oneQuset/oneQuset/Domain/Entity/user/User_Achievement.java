package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User_Achievement {
    @Id
    @GeneratedValue
    private String id;
    private int login_streak;
    private int mission_streak;
    private Long mission_shared_count;
    private Long likes_count;

    @Enumerated(EnumType.STRING)
    private Badge badge;

}
