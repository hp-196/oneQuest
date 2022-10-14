package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import lombok.Getter;

@Getter
public class CancelAcceptNotice {

    private Quest quest;
    private Member member;

    public CancelAcceptNotice(Quest quest, Member joinMember) {
        this.quest = quest;
        this.member = joinMember;
    }
}
