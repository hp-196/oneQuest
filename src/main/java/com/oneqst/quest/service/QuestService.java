package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.QuestDto;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestService {

    private final QuestRepository questRepository;

    public Quest newQuest(QuestDto questDto, Member member) {
        Quest quest = Quest.builder()
                .questTitle(questDto.getQuestTitle())
                .questIntroduce(questDto.getQuestIntroduce())
                .questExplain(questDto.getQuestExplain())
                .questStartTime(questDto.getQuestStartTime())
                .questEndTime(questDto.getQuestEndTime())
                .questUrl(questDto.getQuestUrl())
                .questMaster(member.getNickname())
                .questMember(new HashSet<>())
                .build();
        Quest newQuest = questRepository.save(quest);
        newQuest.addQuestMember(member);
        return newQuest;
    }



}
