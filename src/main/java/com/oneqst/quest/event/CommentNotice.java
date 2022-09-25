package com.oneqst.quest.event;

import com.oneqst.quest.domain.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotice {

    private Comment comment;

    public CommentNotice(Comment comment) {
        this.comment = comment;
    }
}
