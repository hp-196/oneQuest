package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestRepositoryCustom {

    // 모든 퀘스트
    List<Quest> total_quests();

    // 자신이 포함된 퀘스트
    List<Quest> my_quests(Long member_id);

    // 자신이 포함되지 않은 퀘스트
    List<Quest> other_quests(Long member_id);

    // 검색
    List<Quest> Search(Long member_id, String title);

    //페이징 검색
    Page<Quest> search_paging(Member member, String title, Pageable pageable);
}
