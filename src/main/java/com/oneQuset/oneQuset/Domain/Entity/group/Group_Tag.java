package com.oneQuset.oneQuset.Domain.Entity.group;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Color;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group_Tag {
    @Id
    @GeneratedValue
    private Long number; // group_number
    private String tag;
    private String u_id;
}
