package com.oneqst.quest.domain;

import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.Member.domain.Member;
import com.oneqst.notice.NoticeType;
import com.oneqst.tag.Tag;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name = "QUEST_ID")
    private Long id;

    @ManyToMany
    private List<Member> questMaster = new ArrayList<>(); //퀘스트 마스터

    @ManyToMany
    private List<Member> questMember = new ArrayList<>(); //퀘스트 맴버

    @OneToOne(fetch = FetchType.LAZY)
    private Member questHost; //퀘스트 호스트

    @ManyToMany
    private Set<Tag> tags = new HashSet<>(); //태그 리스트

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

    @Enumerated(EnumType.STRING)
    private JoinType joinType; //회원 모집 신청 타입

    @OneToMany(mappedBy = "quest", cascade = CascadeType.REMOVE)
    private List<QuestPost> questPostList = new ArrayList<>(); //퀘스트 포스팅 리스트

    @OneToMany(mappedBy = "quest", cascade = CascadeType.REMOVE)
    private List<AuthPost> authPostList = new ArrayList<>(); //퀘스트 인증 포스팅 리스트

    @OneToMany(mappedBy = "quest", cascade = CascadeType.REMOVE)
    private List<JoinApplication> applicationList = new ArrayList<>(); //참가 신청 리스트


    /*********************** 연관관계 편의 메소드 **********************/

    /**
     * 퀘스트 포스팅 삭제
     */
    public void deletePost(QuestPost questPost) {
        this.questPostList.remove(questPost);
    }

    /**
     * 회원을 퀘스트 마스터에 추가
     */
    public void addQuestMaster(Member member) {
        this.questMaster.add(member);
    }

    /**
     * 회원을 퀘스트 마스터에서 삭제
     */
    public void removeQuestMaster(Member member) {
        this.questMaster.remove(member);
    }

    /**
     * 퀘스트에 회원 추가
     */
    public void addQuestMember(Member member) {
        this.questMember.add(member);
    }

    /**
     * 퀘스트 탈퇴
     */
    public void questWithdraw(Member member) {
        if (this.questMaster.contains(member)) {
            this.getQuestMaster().remove(member);
        }
        this.getQuestMember().remove(member);
    }

    /**
     * 퀘스트 호스트,마스터 여부
     */
    public boolean isHostOrMaster(Member member) {
        return this.questMaster.contains(member) || this.questHost.equals(member);
    }

    /**
     * 퀘스트 회원 여부 확인
     */
    public boolean isMember(MemberInfo memberInfo) {
        return this.questMember.contains(memberInfo.getMember()) || this.questMaster.contains(memberInfo.getMember()) || this.questHost.equals(memberInfo.getMember());
    }

    /**
     * 퀘스트 진행중 확인
     */
    public boolean compareDate() {
        return (this.questEndTime.isEqual(LocalDate.now()) || (this.questEndTime.isAfter(LocalDate.now())));
    }

    /**
     * 태그 추가
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * 태그 전체 삭제
     */
    public void deleteTagAll() {
        tags.removeAll(this.tags);
    }
    /**
     * 태그 출력
     */
    public String returnTags() {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.getTitle());
            sb.append(" ");
        }
        return sb.toString();
    }
}
