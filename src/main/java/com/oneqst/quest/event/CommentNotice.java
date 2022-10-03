package com.oneqst.quest.event;

import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotice {

    private Comment comment;
    private QuestPost questPost;

    public CommentNotice(Comment comment, QuestPost questPost) {
        this.comment = comment;
        this.questPost = questPost;
    }
}
