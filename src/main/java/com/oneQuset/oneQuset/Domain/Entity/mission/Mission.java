package com.oneQuset.oneQuset.Domain.Entity.mission;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mission")
public class Mission {
    /**
     * 미션 데이터,
     * id : 유저의 id
     * title : 미션 제목
     * context : 미션 내용
     * likes : 미션 좋아요 수
     * shard_count : 미션 공유된 수
     * create_data : 미션 생성된 날짜
     * target_data : 미션 목표 날짜
     */
    @Id
    @GeneratedValue
    private Long id;    // mission_community_id
    private String title;
    private String context;
    private Long likes;
    private Long shard_count;
    private LocalDateTime create_data;
    private LocalDateTime target_data;
}
