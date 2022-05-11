package com.oneQuset.oneQuset.Domain.Entity.group;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Color;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group_Option {
    /**
     * 그룹 옵션 데이터,
     * number : 그룹 번호
     * color : 그룹 색상
     */
    @Id
    @GeneratedValue
    private Long number; // group_community_number

    @Enumerated(EnumType.STRING)
    private Color color;
}
