package com.oneqst.quest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.Member.domain.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quest {

    @Id
    @GeneratedValue
    private Long id;

    private String questMaster; //퀘스트 마스터

    @ManyToMany
    private Set<Member> questMember = new HashSet<>(); //퀘스트 맴버

    private String questTitle; //퀘스트 제목

    @Column(unique = true)
    private String questUrl; //퀘스트 주소

    private String questIntroduce; //퀘스트 짧은 설명

    private String questExplain; //퀘스트 긴 설명

    private String questImage; //퀘스트 소개 이미지

    private String questStartTime; //퀘스트 시작 시간

    private String questEndTime; //퀘스트 마감 시간

    private boolean questRecruitEnd; //멤버 모집 여주

    @OneToMany(mappedBy = "quest")
    private List<QuestPost> questPostList = new ArrayList<>();

    /*********************** 연관관계 편의 메소드 **********************/

    public void addPost(QuestPost questPost) {
        this.questPostList.add(questPost);
    }
    public void deletePost(QuestPost questPost) {
        this.questPostList.remove(questPost);
    }
    public void addQuestMember(Member member) {
        this.questMember.add(member);
    }

    public void questWithdraw(Member member) {
        this.getQuestMember().remove(member);
    }


    public boolean isMaster(Member member) {
        return this.questMaster.equals(member.getNickname());
    }

    public boolean isMember(MemberInfo memberInfo) {
        return this.questMember.contains(memberInfo.getMember());
    }

    public boolean isJoinable(MemberInfo memberInfo) {
        Member member = memberInfo.getMember();
        return !this.questMember.contains(member);
    }
}
