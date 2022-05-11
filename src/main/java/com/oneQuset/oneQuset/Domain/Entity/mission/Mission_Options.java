package com.oneQuset.oneQuset.Domain.Entity.mission;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Color;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mission_Options {
    /**
     * 미션 옵션 데이터,
     * number : 미션 번호,
     * id : 유저의 id,
     * color : 미션의 색상
     */
    @Id
    @GeneratedValue
    private Long number;
    private String id;  // user_id
    @Enumerated(EnumType.STRING)
    private Color color;
}
