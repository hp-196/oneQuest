package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.notice.Notice;
import com.oneqst.notice.NoticeRepository;
import com.oneqst.notice.NoticeType;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.repository.QuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Async
@Transactional
@RequiredArgsConstructor
public class QuestNoticeListener {

    private final NoticeRepository noticeRepository;
    private final QuestPostRepository questPostRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글 알림
     */
    @EventListener
    public void commentPostEvent(CommentNotice commentNotice) {
        Comment comment = commentNotice.getComment();
        QuestPost questPost = questPostRepository.findById(commentNotice.getQuestPost().getId()).get();
        Notice notice = new Notice();
        notice.setTitle(questPost.getTitle()); //퀘스트 포스팅 제목
        notice.setMember(questPost.getWriter()); //알림받을 멤버
        notice.setContent(comment.getContent()); //댓글 내용
        notice.setByMember(comment.getWriter().getNickname()); //알림당사자 닉네임
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setUrl("/quest/" + questPost.getQuest().getQuestUrl() + "/post/" + questPost.getId());
        notice.setNoticeType(NoticeType.POST_COMMENT);
        noticeRepository.save(notice);
    }

    /**
     * 멤버 초대 알림
     */
    @EventListener
    public void inviteMemberEvent(InviteNotice inviteNotice) {
        String nameOrEmail = inviteNotice.getNickNameOrEmail();
        Member member = memberRepository.findByNicknameOrEmail(nameOrEmail, nameOrEmail);
        Notice notice = new Notice();
        notice.setMember(member); //초대받은 멤버
        notice.setByMember(inviteNotice.getMember().getNickname()); //초대한 멤버
        notice.setTitle(inviteNotice.getQuest().getQuestTitle()); //퀘스트 제목
        notice.setContent("링크를 클릭하면 가입이 완료됩니다.");
        notice.setUrl("/quest/"+inviteNotice.getQuest().getQuestUrl()+"/join");
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setNoticeType(NoticeType.QUEST_INVITE);
        noticeRepository.save(notice);
    }

    /**
     * 인증 포스팅 알림
     */
    @EventListener
    public void scoreEvent(ScoreNotice scoreNotice) {
        Member member = memberRepository.findById(scoreNotice.getMember().getId()).get();
        Notice notice = new Notice();
        notice.setNoticeTime(LocalDateTime.now());
        notice.setMember(scoreNotice.getAuthPost().getWriter());
        notice.setChecked(false);
        notice.setTitle(scoreNotice.getAuthPost().getTitle());
        notice.setContent("링크를 클릭하면 해당 게시글로 이동합니다.");
        notice.setByMember(member.getNickname());
        notice.setUrl("/quest/"+scoreNotice.getQuest().getQuestUrl()+"/auth/post/"+scoreNotice.getAuthPost().getId());
        notice.setNoticeType(NoticeType.QUEST_AUTH);
        noticeRepository.save(notice);
    }

    /**
     * 퀘스트 가입 신청 후 승인 알림
     */
    @EventListener
    public void acceptEvent(JoinAcceptNotice acceptNotice) {
        Quest quest = acceptNotice.getQuest();
        Member member = acceptNotice.getMember();
        Notice notice = new Notice();
        notice.setTitle(quest.getQuestTitle()); //퀘스트 제목
        notice.setContent("링크를 클릭하면 해당 퀘스트로 이동합니다.");
        notice.setMember(member);
        notice.setNoticeType(NoticeType.JOIN_WAITING);
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setUrl("/quest/" + quest.getQuestUrl());
        noticeRepository.save(notice);
    }

    /**
     * 퀘스트 가입 취소 후 승인 알림
     */
    @EventListener
    public void cancelEvent(CancelAcceptNotice acceptNotice) {
        Quest quest = acceptNotice.getQuest();
        Member member = acceptNotice.getMember();
        Notice notice = new Notice();
        notice.setTitle(quest.getQuestTitle()); //퀘스트 제목
        notice.setContent("링크를 클릭하면 해당 퀘스트로 이동합니다.");
        notice.setMember(member);
        notice.setNoticeType(NoticeType.JOIN_CANCEL);
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setUrl("/quest/" + quest.getQuestUrl());
        noticeRepository.save(notice);
    }

}
