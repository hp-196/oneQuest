package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.service.AuthService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ScoreNotice {

    private Member member;
    private AuthPost authPost;
    private Quest quest;
    private int sc;

    public ScoreNotice(Member member, AuthPost authPost, Quest quest, int sc) {
        this.member = member;
        this.authPost = authPost;
        this.quest = quest;
        this.sc = sc;
    }
}
