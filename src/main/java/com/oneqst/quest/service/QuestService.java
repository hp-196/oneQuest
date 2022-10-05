package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.*;
import com.oneqst.quest.event.InviteNotice;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;
    private final ApplicationEventPublisher eventPublisher;

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
                .questMember(new ArrayList<>())
                .questMaster(new ArrayList<>())
                .questHost(member)
                .build();
        Quest newQuest = questRepository.save(quest);
        newQuest.addQuestMember(member);
        return newQuest;
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
        return questPostRepository.save(questPost);
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
    public void questWithdraw(Quest quest, Member member) {
        deleteAllQuestPost(member);
        quest.questWithdraw(member);
        if (quest.getQuestMaster().size() + quest.getQuestMember().size() == 0) {
            deleteQuest(quest);
        }
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
