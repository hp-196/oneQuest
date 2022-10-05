package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SettingService {


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
}
