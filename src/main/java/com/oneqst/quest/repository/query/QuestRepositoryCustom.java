package com.oneqst.quest.repository.query;

import com.oneqst.quest.domain.Quest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestRepositoryCustom {

    // 모든 퀘스트
    List<Quest> total_quests();

    // 자신이 포함된 퀘스트
    List<Quest> my_quests(Long member_id);

    // 자신이 포함되지 않은 퀘스트
    List<Quest> other_quests(Long member_id);
}
