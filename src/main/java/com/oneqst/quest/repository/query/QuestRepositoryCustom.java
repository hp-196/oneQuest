package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestRepositoryCustom {

    // 모든 퀘스트
    List<Quest> totalQuests();

    // 자신이 포함된 퀘스트
    List<Quest> myQuests(Long memberId);

    // 자신이 포함되지 않은 퀘스트
    List<Quest> otherQuests(Long memberId);

    // 검색
    List<Quest> search(Long memberId, String title);

    //페이징 검색
    Page<Quest> searchPaging(Member member, String title, Pageable pageable);
}
