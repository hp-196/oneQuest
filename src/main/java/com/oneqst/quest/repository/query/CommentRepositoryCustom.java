package com.oneqst.quest.repository.query;

import com.oneqst.quest.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    public List<Comment> myComment(Long memberId);
}
