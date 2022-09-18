package com.oneqst.quest.domain;

import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.Member.domain.Member;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Quest {

    @Id
    @GeneratedValue
    @Column(name = "Quest_id")
    private Long id;

    @ManyToMany
    private List<Member> questMaster = new ArrayList<>(); //퀘스트 마스터

    @ManyToMany
    private List<Member> questMember = new ArrayList<>(); //퀘스트 맴버

    private String questTitle; //퀘스트 제목

    @Column(unique = true)
    private String questUrl; //퀘스트 주소

    private String questIntroduce; //퀘스트 짧은 설명

    private String questExplain; //퀘스트 긴 설명

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String questImage; //퀘스트 소개 이미지

    private LocalDate questStartTime; //퀘스트 시작 시간

    private LocalDate questEndTime; //퀘스트 마감 시간

    private boolean questRecruitEnd; //멤버 모집 여부

    @OneToMany(mappedBy = "quest", cascade = CascadeType.REMOVE)
    private List<QuestPost> questPostList = new ArrayList<>();


    /*********************** 연관관계 편의 메소드 **********************/

    public void deletePost(QuestPost questPost) {
        this.questPostList.remove(questPost);
    }
    public void addQuestMaster(Member member) {
        this.questMaster.add(member);
    }
    public void addQuestMember(Member member) {
        this.questMember.add(member);
    }

    public void questWithdraw(Member member) {
        if (this.questMaster.contains(member)) {
            this.getQuestMaster().remove(member);
        }
        this.getQuestMember().remove(member);
    }


    public boolean isMaster(Member member) {
        return this.questMaster.contains(member);
    }

    public boolean isMember(MemberInfo memberInfo) {
        return this.questMember.contains(memberInfo.getMember()) || this.questMaster.contains(memberInfo.getMember());
    }

    public boolean isJoinable(MemberInfo memberInfo) {
        Member member = memberInfo.getMember();
        return !this.questMember.contains(member);
    }

    public List<Member> allList() {
        List<Member> memberList = new ArrayList<>();
        memberList.addAll(this.questMaster);
        memberList.addAll(this.questMember);
        return memberList;
    }

    public boolean compareDate() {
        return (this.questEndTime.isEqual(LocalDate.now()) ? true : this.questEndTime.isAfter(LocalDate.now()) ? true : false);
    }
}
