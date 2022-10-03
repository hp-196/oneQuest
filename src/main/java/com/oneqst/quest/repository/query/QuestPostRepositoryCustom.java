package com.oneqst.quest.repository.query;

import com.oneqst.quest.dto.MyQuestPostDto;

import java.util.List;

public interface QuestPostRepositoryCustom {
    public List<MyQuestPostDto> myQuestPost(Long memberId);
}
