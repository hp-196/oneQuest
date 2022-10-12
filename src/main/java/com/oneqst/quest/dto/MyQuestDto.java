package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MyQuestDto {
    private String Url;
    private String title;
    private String content;
    private LocalDate startTime;
    private LocalDate endTime;

    @QueryProjection
    public MyQuestDto(String url, String title, String content, LocalDate startTime, LocalDate endTime) {
        this.Url = url;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
