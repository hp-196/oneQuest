package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestRepositoryCustom {
    // 자신이 가입된 퀘스트
    List<Quest> totalQuests(Long memberId);

    // 자신이 관리자인 퀘스트
    List<Quest> masterQuests(Long memberId);

    List<Quest> memberQuests(Long memberId);

    // 자신이 포함되지 않은 퀘스트
    List<Quest> otherQuests(Long memberId);

    // 검색
    List<Quest> search(Long memberId, String title);

    //페이징 검색
    Page<Quest> searchPaging(Member member, String title, Pageable pageable);
}
