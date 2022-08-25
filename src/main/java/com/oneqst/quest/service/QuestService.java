package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.QuestComment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.dto.*;
import com.oneqst.quest.repository.QuestCommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;
    private final QuestCommentRepository questCommentRepository;

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
                .questMaster(member.getNickname())
                .questImage(questDto.getQuestImage())
                .questMember(new HashSet<>())
                .build();
        Quest newQuest = questRepository.save(quest);
        newQuest.addQuestMember(member);
        return newQuest;
    }

    /**
     * 퀘스트 포스팅
     */
    public QuestPost questPost(QuestPostDto questPostDto, Quest quest, Member member) {
        QuestPost questPost = QuestPost.builder()
                .title(questPostDto.getTitle())
                .content(questPostDto.getContent())
                .build();
        questPost.setWriter(member);
        questPost.setQuest(quest);
        questPost.setPostTime(LocalDateTime.now());
        QuestPost newQuestPost = questPostRepository.save(questPost);
        //quest.addPost(newQuestPost);
        //member.addPost(newQuestPost);
        return newQuestPost;
    }

    /**
     * 퀘스트 공지사항 포스팅
     */
    public QuestPost questNoticePost(QuestPostDto questPostDto, Quest quest, Member member) {
        QuestPost questPost = QuestPost.builder()
                .title(questPostDto.getTitle())
                .content(questPostDto.getContent())
                .build();
        questPost.setWriter(member);
        questPost.setQuest(quest);
        questPost.setPostTime(LocalDateTime.now());
        questPost.setNotice(true);
        QuestPost newQuestPost = questPostRepository.save(questPost);
        return newQuestPost;
    }


    /**
     * 퀘스트 멤버 추가
     */
    public void addQuestMember(Quest quest, Member member) {
        quest.addQuestMember(member);
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
     * 퀘스트 포스팅 수정
     */
    public void updateQuestPost(QuestPost questPost, QuestPostUpdateDto questPostUpdateDto) {
        questPost.setTitle(questPostUpdateDto.getTitle());
        questPost.setContent(questPostUpdateDto.getContent());
        questPostRepository.save(questPost);
    }

    /**
     * 퀘스트 탈퇴
     */
    public void questWithdraw(Quest quest, Member member) {
        quest.questWithdraw(member);
    }


    /**
     * 퀘스트 포스팅 삭제
     */
    public void deleteQuestPost(QuestPost questPost) {
        questPostRepository.delete(questPost);
    }


    /**
     * 해당 포스팅 댓글 전체 조회
     */
    public List<QuestComment> findCommentAll(QuestPost questPost, Long id) {
        questPost = questPostRepository.getOne(id);
        List<QuestComment> commentList = questCommentRepository.findByPost(questPost);
        return commentList;
    }

    /**
     * 댓글 POST
     */
    public QuestComment commentPost(Member member, QuestPost questPost, CommentDto commentDto) {
        QuestComment questComment = QuestComment.builder()
                .content(commentDto.getContent())
                .build();
        member.addCommentList(questComment);
        questPost.addCommentList(questComment);
        questCommentRepository.save(questComment);
        return questComment;
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Member member, QuestPost questPost, QuestComment questComment) {
        member.removeComment(questComment);
        questPost.removeComment(questComment);
        questCommentRepository.delete(questComment);
    }
}
