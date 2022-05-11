package com.oneQuset.oneQuset.Domain.Entity.group;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Clob;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter @Setter
public class Group {
    @Id
    @GeneratedValue
    private Long gc_Number;
    private String name;
    private String  group_image;
    private String notice;
    private Timestamp create_data;


}
