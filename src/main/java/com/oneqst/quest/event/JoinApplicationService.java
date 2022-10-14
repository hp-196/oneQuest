package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.JoinApplication;
import com.oneqst.quest.domain.JoinType;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.JoinApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JoinApplicationService {

    private final JoinApplicationRepository joinApplicationRepository;

    public void newWaitingMember(Quest quest, Member joinMember) {
        JoinApplication joinApplication = JoinApplication.builder()
                .quest(quest)
                .member(joinMember)
                .time(LocalDateTime.now())
                .accepted(false)
                .build();
        joinApplicationRepository.save(joinApplication);
    }

    public void changeState(Quest quest) {
        if (quest.getJoinType() == JoinType.NORMAL) {
            quest.setJoinType(JoinType.WAITING);
        } else
            quest.setJoinType(JoinType.NORMAL);
    }
}
