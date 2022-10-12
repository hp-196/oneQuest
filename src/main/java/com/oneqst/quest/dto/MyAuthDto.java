package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyAuthDto {
    private String questUrl;
    private String questTitle;
    private Long authId;
    private String authTitle;
    private String content;
    private LocalDateTime time;

    @QueryProjection
    public MyAuthDto(String questUrl, String questTitle, Long authId, String authTitle, String content, LocalDateTime time) {
        this.questUrl = questUrl;
        this.questTitle = questTitle;
        this.authId = authId;
        this.authTitle = authTitle;
        this.content = content;
        this.time = time;
    }
}
