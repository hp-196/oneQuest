package com.oneQuset.oneQuset.Domain.Entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class User_Auth {
    @Id
    @GeneratedValue
    private String id;
    private Long number;
    private String email;

    @Enumerated(EnumType.STRING)
    private Provider provide;
    private LocalDateTime create_date;
}
