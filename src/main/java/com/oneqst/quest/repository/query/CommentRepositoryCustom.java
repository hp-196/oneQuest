package com.oneqst.quest.repository.query;

import com.oneqst.quest.dto.MyCommentDto;

import java.util.List;

public interface CommentRepositoryCustom {
    public List<MyCommentDto> myCommentLookup(Long memberId);
}
