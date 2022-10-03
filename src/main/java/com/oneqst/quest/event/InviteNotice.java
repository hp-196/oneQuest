package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.InviteDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteNotice {

    private String nickNameOrEmail;
    private Member member;
    private Quest quest;


    public InviteNotice(InviteDto inviteDto, Member member, Quest quest) {
        this.nickNameOrEmail = inviteDto.getNickNameOrEmail();
        this.member = member;
        this.quest = quest;
    }
}
