package com.oneQuset.oneQuset.Domain.Entity.mission;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Mission {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String context;
    private Long likes;
    private Long shard_count;
    private LocalDateTime create_data;
    private LocalDateTime target_data; // 목표
}
