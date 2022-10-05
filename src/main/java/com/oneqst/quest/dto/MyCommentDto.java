package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCommentDto {
    private String questTitle;
    private String postTitle;
    private String content;
    private LocalDateTime time;

    @QueryProjection
    public MyCommentDto(String questTitle, String postTitle, String content, LocalDateTime time) {
        this.questTitle = questTitle;
        this.postTitle = postTitle;
        this.content = content;
        this.time = time;
    }
}
