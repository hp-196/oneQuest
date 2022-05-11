package com.oneQuset.oneQuset.Domain.Entity.mission;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Mission_Community {
    @Id
    @GeneratedValue
    private Long number;
    private String id;
    @Enumerated(EnumType.STRING)
    private Role role;
}
