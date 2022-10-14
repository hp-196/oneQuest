package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class JoinAcceptNotice {

    private Quest quest;
    private Member member;

    public JoinAcceptNotice(Quest quest, Member joinMember) {
        this.quest = quest;
        this.member = joinMember;
    }
}
