package com.oneQuset.oneQuset.Domain.Entity.group;

import com.oneQuset.oneQuset.Domain.Entity.enum_type.Role;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_community")
public class Group_Community {
    /**
     * 그룹 커뮤니티 데이터,
     * number : 그룹 번호,
     * id : 유저의 id,
     * join_date : 가입 날짜,
     * role : 유저의 역활
     */
    @Id
    @GeneratedValue
    private Long number;
    private String id;    // user_id
    private Timestamp join_date;

    @Enumerated(EnumType.STRING)
    private Role role;
}
