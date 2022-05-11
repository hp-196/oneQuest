package com.oneQuset.oneQuset.Domain.Entity.mission;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mission_tag")
public class Mission_Tag {
    /**
     * 미션의 태그 데이터,
     * number : 미션의 번호,
     * tag : 미션의 태그
     */
    @Id
    @GeneratedValue
    private Long number;    // mission_number
    private String tag;
}
