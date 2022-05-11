package com.oneQuset.oneQuset.Domain.Entity.group;

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
    private Long g_number;
    private String tag;
    private String u_id;

    @Enumerated(EnumType.STRING)
    private Color color;
}
