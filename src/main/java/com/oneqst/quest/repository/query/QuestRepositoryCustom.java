package com.oneqst.quest.repository.query;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.MyQuestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestRepositoryCustom {

    // 자신이 포함된 퀘스트
    List<Quest> myQuests(Long memberId);

    // 자신이 포함되지 않은 퀘스트
    List<Quest> otherQuests(Long memberId);

    // 검색
    List<Quest> search(Long memberId, String title);

    /**
     * 해당 유저가 관리자인 퀘스트 조회
     * @param memberId  유저의 id
     * @return  해당 유저가 관리자인 퀘스트 목록
     */
    List<Quest> questMaster(Long memberId);

    /**
     * 해당 유저가 참여한 퀘스트 조회
     * @param memberId  유저의 id
     * @return  해당 유저가 참여한 퀘스트 목록
     */
    List<Quest> questMember(Long memberId);

    //페이징 검색
    Page<Quest> searchPaging(Member member, String title, Pageable pageable);

    List<MyQuestDto> myActivityQuestLookup(Long memberId);

    List<Quest> findRandom9(Member member);

    Page<Quest> totalSearchPaging(Member member, Pageable pageable, String search);
}
