package com.oneQuset.oneQuset.Domain.Entity.group;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Enum Role;
}
