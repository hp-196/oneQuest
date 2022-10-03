package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyQuestPostDto {
    private String questTitle;
    private String title;
    private String content;
    private LocalDateTime time;

    @QueryProjection
    public MyQuestPostDto(String questTitle, String title, String content, LocalDateTime time) {
        this.questTitle = questTitle;
        this.title = title;
        this.content = content;
        this.time = time;
    }
}
