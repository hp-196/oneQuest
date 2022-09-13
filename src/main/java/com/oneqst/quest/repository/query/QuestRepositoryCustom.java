package com.oneqst.quest.repository.query;

import com.oneqst.quest.domain.Quest;

import java.util.List;

public interface QuestRepositoryCustom {
    List<Quest> search(String user,String questTitle);

    List<Quest> total_search(Long member_id);
}
