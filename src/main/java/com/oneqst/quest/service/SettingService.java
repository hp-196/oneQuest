package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SettingService {

    private final QuestRepository questRepository;

    /**
     * 멤버 -> 매니저 승격
     */
    public void addManager(Quest quest, Member questMember) {
        quest.addQuestMaster(questMember);
    }


    /**
     * 매니저 -> 멤버 강등
     */
    public void relegation(Quest quest, Member manager) {
        quest.removeQuestMaster(manager);
    }

    /**
     * 모집여부 변경
     */
    public void recruitment(Quest quest) {
        boolean questRecruitEnd = quest.isQuestRecruitEnd();
        if (questRecruitEnd) {
            quest.setQuestRecruitEnd(false);
        } else quest.setQuestRecruitEnd(true);
    }

    /**
     * 호스트 임명
     */
    public void assignHost(Quest quest, Member host, Member manager) {
        quest.setQuestHost(manager);
        quest.removeQuestMaster(manager);
        quest.addQuestMaster(host);
    }

    /**
     * 호스트 전용 퀘스트 삭제
     */
    public boolean deleteQuest(Quest quest) {
        if (!(quest.getQuestMaster().isEmpty() && quest.getQuestMember().size()==1)) {
            return false;
        }
        questRepository.delete(quest);
        return true;
    }
}
