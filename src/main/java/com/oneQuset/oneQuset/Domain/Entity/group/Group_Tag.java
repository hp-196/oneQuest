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
@Table(name = "group_tag")
public class Group_Tag {
    /**
     * 그룹 태그 데이터,
     * number : 그룹 번호
     * tag : 그룹 태그
     * id : 유저의 id
     */
    @Id
    @GeneratedValue
    private Long number; // group_number
    private String tag;
    private String id;    // user_id
}
