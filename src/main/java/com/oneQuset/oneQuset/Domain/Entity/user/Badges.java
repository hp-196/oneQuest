package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Badges {
    @Id
    @GeneratedValue
    private String badge;
    @Enumerated(EnumType.STRING)
    private Target target;
    private String image;   // CLOB
    private int goal;
}
