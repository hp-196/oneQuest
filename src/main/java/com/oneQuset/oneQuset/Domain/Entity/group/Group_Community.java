package com.oneQuset.oneQuset.Domain.Entity.group;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group_Community {
    @Id
    @GeneratedValue
    private Long number;
    private String u_id;
    private Timestamp join_date;

    @Enumerated(EnumType.STRING)
    private Role role;
}
