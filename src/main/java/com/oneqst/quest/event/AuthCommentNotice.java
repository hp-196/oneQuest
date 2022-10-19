package com.oneqst.quest.event;

import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Comment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class AuthCommentNotice {

    private AuthPost authPost;
    private Comment comment;

    public AuthCommentNotice(Comment comment, AuthPost authPost) {
        this.authPost = authPost;
        this.comment = comment;
    }
}
