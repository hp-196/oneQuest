package com.oneQuset.oneQuset.Domain.Entity.group;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Clob;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group")
public class Group {
    /**
     * 그룹 데이터,
     * number :  그룹의 고유 번호
     * name : 그룹 이름
     * group_image : 그룹 이미지
     * notice : 그룹 공지
     * create_data : 그룹 생성 일자
     */
    @Id
    @GeneratedValue
    private Long number;    // group_community_number
    private String name;
    private String  group_image;
    private String notice;
    private Timestamp create_data;


}
