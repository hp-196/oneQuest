package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.notice.Notice;
import com.oneqst.notice.NoticeRepository;
import com.oneqst.notice.NoticeType;
import com.oneqst.quest.domain.JoinType;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.*;
import com.oneqst.quest.event.InviteNotice;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.repository.TagRepository;
import com.oneqst.tag.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final NoticeRepository noticeRepository;
    private final AuthService authService;
    private final TagRepository tagRepository;

    /**
     * 퀘스트 생성
     */
    public Quest newQuest(QuestDto questDto, Member member) {
        Quest quest = Quest.builder()
                .questTitle(questDto.getQuestTitle())
                .questIntroduce(questDto.getQuestIntroduce())
                .questExplain(questDto.getQuestExplain())
                .questStartTime(questDto.getQuestStartTime())
                .questEndTime(questDto.getQuestEndTime())
                .questUrl(questDto.getQuestUrl())
                .questImage(questDto.getQuestImage())
                .questRecruitEnd(true)
                .questMember(new ArrayList<>())
                .questMaster(new ArrayList<>())
                .tags(new HashSet<>())
                .questHost(member)
                .joinType(JoinType.NORMAL)
                .build();
        Quest newQuest = questRepository.save(quest);
        tagParsing(questDto.getTag(), newQuest);
        newQuest.addQuestMember(member);
        return newQuest;
    }

    /**
     * 태그 파싱
     */
    public void tagParsing(String tags, Quest quest) {
        Pattern pattern = Pattern.compile("(#[\\d|A-Z|a-z|가-힣|가-힣]*)");
        Matcher mat = pattern.matcher(tags);
        while (mat.find()) {
            String title = mat.group(0);
            if (!title.equals("#")) {
                Tag tag = tagRepository.findByTitle(title);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTitle(title);
                    tagRepository.save(tag);
                }
                quest.addTag(tag);
//                System.out.println(title);
            }
        }
    }

    /**
     * 퀘스트 수정
     */
    public void questUpdate(Quest quest, QuestUpdateDto questUpdateDto) {
        quest.setQuestTitle(questUpdateDto.getQuestTitle());
        quest.setQuestIntroduce(questUpdateDto.getQuestIntroduce());
        quest.setQuestExplain(questUpdateDto.getQuestExplain());
        quest.setQuestStartTime(questUpdateDto.getQuestStartTime());
        quest.setQuestEndTime(questUpdateDto.getQuestEndTime());
        quest.setQuestUrl(questUpdateDto.getQuestUrl());
        quest.setQuestImage(questUpdateDto.getQuestImage());
        questRepository.save(quest);
        quest.deleteTagAll();
        tagParsing(questUpdateDto.getTag(), quest);
    }

    /**
     * 퀘스트 포스팅
     */
    public QuestPost questPost(QuestPostDto questPostDto, Quest quest, Member member) {
        QuestPost questPost = QuestPost.builder()
                .title(questPostDto.getTitle())
                .content(questPostDto.getContent())
                .postImage(questPostDto.getPostImage())
                .writer(member)
                .quest(quest)
                .postTime(LocalDateTime.now())
                .build();

        return questPostRepository.save(questPost);
    }

    /**
     * 퀘스트 공지사항 포스팅
     */
    public QuestPost questNoticePost(QuestPostDto questPostDto, Quest quest, Member member) {
        QuestPost questPost = QuestPost.builder()
                .title(questPostDto.getTitle())
                .content(questPostDto.getContent())
                .postImage(questPostDto.getPostImage())
                .build();
        questPost.setWriter(member);
        questPost.setQuest(quest);
        questPost.setPostTime(LocalDateTime.now());
        questPost.setNotice(true);
        QuestPost newQuestPost = questPostRepository.save(questPost);
        noticeEvent(newQuestPost,quest,member);
        return newQuestPost;

    }

    public void noticeEvent(QuestPost newQuestPost, Quest quest, Member member) {
        List<Member> memberListExcludeWriter = quest.getQuestMember();
        for (Member m : memberListExcludeWriter) {
            if (!m.equals(newQuestPost.getWriter())) {
                Notice notice = new Notice();
                notice.setTitle(quest.getQuestTitle());
                notice.setContent("링크를 클릭하면 해당 공지사항으로 이동합니다.");
                notice.setByMember(member.getNickname());
                notice.setMember(m);
                notice.setNoticeType(NoticeType.NOTICE_POSTING);
                notice.setNoticeTime(LocalDateTime.now());
                notice.setChecked(false);
                notice.setUrl("/quest/" + quest.getQuestUrl() + "/post/" + newQuestPost.getId());
                noticeRepository.save(notice);
            }
        }
    }


    /**
     * 퀘스트 포스팅 수정
     */
    public void updateQuestPost(QuestPost questPost, QuestPostUpdateDto questPostUpdateDto) {
        questPost.setTitle(questPostUpdateDto.getTitle());
        questPost.setContent(questPostUpdateDto.getContent());
        questPostRepository.save(questPost);
    }

    /**
     * 퀘스트 포스팅 삭제
     */
    public void deleteQuestPost(QuestPost questPost) {
        questPostRepository.delete(questPost);
    }

    /**
     * 퀘스트 멤버 추가
     */
    public void addQuestMember(Quest quest, Member member) {
        if (!quest.getQuestMember().contains(member)) {
            quest.addQuestMember(member);
        }
    }


    /**
     * 퀘스트 탈퇴
     */
    public boolean questWithdraw(Quest quest, Member member) {
        if (quest.getQuestHost().equals(member)) {
            return false;
        }
        deleteAllQuestPost(member);
        authService.deleteAllPost(member);
        quest.questWithdraw(member);
        if (quest.getQuestMaster().size() + quest.getQuestMember().size() == 0) {
            deleteQuest(quest);
        }
        return true;
    }

    private void deleteAllQuestPost(Member member) {
        List<QuestPost> questPostList = questPostRepository.findByWriter(member);
        questPostRepository.deleteAll(questPostList);
    }

    /**
     * 퀘스트 삭제
     */
    private void deleteQuest(Quest quest) {
        questRepository.delete(quest);
    }


    /**
     * 퀘스트 멤버 초대
     */
    public boolean inviteMember(InviteDto inviteDto, Member member, Quest quest) {
        String name = inviteDto.getNickNameOrEmail();
        if (name.equals(member.getNickname()) || name.equals(member.getEmail())) {
            log.info("자기자신을 초대할순 없습니다");
            return false;
        }
        eventPublisher.publishEvent(new InviteNotice(inviteDto, member, quest));
        return true;
    }

    /**
     * 해당 유저가 관리자인 퀘스트를 조회
     * @param memberId  유저의 id
     * @return  해당 유저가 관리자인 퀘스트 목록
     */
    public List<Quest> masterQuestLookup(Long memberId) {
        return questRepository.questMaster(memberId);
    }

    /**
     * 해당 유저가 참여한 퀘스트를 조회
     * @param memberId  유저의 id
     * @return  해당 유저가 참여한 퀘스트 목록
     */
    public List<Quest> memberQuestLookup(Long memberId) {
        return questRepository.questMember(memberId);
    }
}
