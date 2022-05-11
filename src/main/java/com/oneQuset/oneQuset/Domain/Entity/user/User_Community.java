package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.user.User_Community_Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User_Community {
    @Id
    @GeneratedValue
    private Long number;
    private String id;

    @Enumerated(EnumType.STRING)
    private User_Community_Type user_community_type;
    private String target_id;
}
