package com.oneQuset.oneQuset.Domain.Entity.user;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.user.Target;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Badges {
    /**
     * 뱃지 데이터,
     * badge : 유저의 뱃지,
     * target : 목표 종류,
     * image : 뱃지의 이미지,
     * goal : 성취감 목표
     */
    @Id
    @GeneratedValue
    private String badge;
    @Enumerated(EnumType.STRING)
    private Target target;
    private String image;   // CLOB
    private int goal;
}
