package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCommentDto {
    private String questUrl;
    private String questTitle;
    private Long postId;
    private String postTitle;
    private String content;
    private LocalDateTime time;

    @QueryProjection
    public MyCommentDto(String questUrl, String questTitle, Long postId, String postTitle, String content, LocalDateTime time) {
        this.questUrl = questUrl;
        this.questTitle = questTitle;
        this.postId = postId;
        this.postTitle = postTitle;
        this.content = content;
        this.time = time;
    }
}
