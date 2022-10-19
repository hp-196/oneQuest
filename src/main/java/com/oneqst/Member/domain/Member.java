package com.oneqst.Member.domain;

import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.domain.Score;
import com.oneqst.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String nickname; //닉네임

    private String password; //비번

    @Column(unique = true)
    private String email; //이메일

    private boolean emailAuth; //이메일 인증 여부

    private String emailToken; //이메일 인증 토큰값

    private LocalDateTime signUpTime; //회원가입 시간

    private String introduce; //자기소개

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String profileImage; //프로필 이미지

    private String job; //직업

    private String url; //sns 주소

    private String address; //집 주소

    @ManyToMany
    private Set<Tag> tags;

    private boolean emailAlarm; //이메일 알람 여부

    private boolean webAlarm; //웹 알람 여부

    @OneToMany(mappedBy = "writer")
    private List<QuestPost> postList = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Score> scoreList = new ArrayList<>();



    /*********************** 연관관계 편의 메소드 **********************/

    public void removePost(QuestPost questPost) {
        this.postList.remove(questPost);
    }

    public void EmailTokenCreate() {
        this.emailToken = UUID.randomUUID().toString();
    }

    public boolean isEmailChecked(MemberInfo memberInfo) {
        return memberInfo.getMember().isEmailAuth();
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
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

    public void deleteTagAll() {
        tags.removeAll(this.tags);
    }
}
