package com.oneqst.quest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MyCommentDto {
    private String title;  // 퀘스트 제목
    private String postTitle;   // 포스트 제먹
    private String writer;  // 댓글 작성자
    private String content; // 댓글 내용
    private LocalDateTime postTime; // 댓글 작성 시간

    @QueryProjection
    public MyCommentDto(String title, String postTitle, String writer, String content, LocalDateTime postTime) {
        this.title = title;
        this.postTitle = postTitle;
        this.writer = writer;
        this.content = content;
        this.postTime = postTime;
    }
}
