package com.oneqst.quest.dto;

import com.oneqst.quest.domain.AuthPost;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AuthPostUpdateDto {

    private String title;

    private String content;

    private String postImage;

    public AuthPostUpdateDto(AuthPost authPost) {
        this.title = authPost.getTitle();
        this.content = authPost.getContent();
        this.postImage = authPost.getPostImage();
    }
}
