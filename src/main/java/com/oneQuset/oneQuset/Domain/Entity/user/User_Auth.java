package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class User_Auth {
    @Id
    @GeneratedValue
    private String id;
    private Long number;
    private String email;
    private Enum provide;
    private LocalDateTime create_date;
}
