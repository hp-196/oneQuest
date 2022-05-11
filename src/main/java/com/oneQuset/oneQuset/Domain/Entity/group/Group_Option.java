package com.oneQuset.oneQuset.Domain.Entity.group;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Color;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group_Option {
    @Id
    @GeneratedValue
    private Long number; // group_community_number

    @Enumerated(EnumType.STRING)
    private Color color;
}
