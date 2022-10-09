package com.oneqst.quest.repository;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.query.QuestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface QuestRepository extends JpaRepository<Quest, Long>, QuestRepositoryCustom {
    // 퀘스트 url 검색
    Quest findByQuestUrl(String url);

    // 퀘스트 멤버에 포함하지 않는지 탐색
    List<Quest> findByQuestMemberNotContaining(Member member);

    // 퀘스트 멤버에 포함하는지 탐색
    List<Quest> findByQuestMemberContaining(Member member);

    // 검색 기능
    // 퀘스트 멤버가 동일하고, 퀘스트 이름이 포함되는지
    // querydsl 로 변경 예정
    List<Quest> findByQuestMemberEqualsAndQuestTitleIsContaining(Member member, String str);

    //제목 검색
    List<Quest> findByQuestTitleContainingAndQuestMemberNotContains(String title, Member member);

    //무작위 9개 추출. db성능 부하 있을시 교체할것
    @Query(value = "select * from Quest order by rand() limit 9", nativeQuery = true)
    List<Quest> findRandom();

    List<Quest> findFirst9ByQuestRecruitEndAndQuestMemberNotContaining(boolean b, Member member);

    boolean existsByQuestUrl(String url);
}