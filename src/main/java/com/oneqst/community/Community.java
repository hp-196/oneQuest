package com.oneqst.community;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member communityCreateMember; //커뮤니티 생성 멤버

    @ManyToOne
    private Quest communityQuest; //퀘스트-커뮤니티

    @OneToMany(mappedBy = "community")
    private List<CommunityJoin> communityJoinList; //커뮤니티 참여

    @Column(nullable = false)
    private String communityTitle; //커뮤니티 제목

    @Column(nullable = false)
    private String communityContent; //커뮤니티 내용

    @Column(nullable = false)
    private LocalDateTime communityCreateTime; //커뮤니티 생성 시간

    @Column(nullable = false)
    private LocalDateTime communityStartTime; //커뮤니티 시작 시간

    @Column(nullable = false)
    private LocalDateTime communityEndTime; //커뮤니티 종료 시간






}
