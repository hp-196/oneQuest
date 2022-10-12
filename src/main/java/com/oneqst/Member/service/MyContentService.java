package com.oneqst.Member.service;

import com.oneqst.quest.dto.MyAuthDto;
import com.oneqst.quest.dto.MyCommentDto;
import com.oneqst.quest.dto.MyQuestDto;
import com.oneqst.quest.dto.MyQuestPostDto;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 나의 포스트, 댓글 조회용 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyContentService {
    private final QuestRepository questRepository;
    private final QuestPostRepository questPostRepository;
    private final CommentRepository commentRepository;

    private final AuthPostRepository authPostRepository;


    /**
     * 나의 포스트 조회
     *
     * @param memberId 유저의 id
     * @return 포스트 리스트
     */
    public List<MyQuestPostDto> myActivityQuestPostLookup(Long memberId) {
        return questPostRepository.myQuestPostLookup(memberId);
    }

    /**
     * 나의 댓글 조회
     *
     * @param memberId 유저의 id
     * @return 댓글 리스트
     */
    public List<MyCommentDto> myActivityCommentLookup(Long memberId) {
        return commentRepository.myCommentLookup(memberId);
    }

    /**
     * 나의 활동 퀘스트 조회
     * @param memberId  유저의 Id
     * @return  퀘스트 리스트
     */
    public List<MyQuestDto> myActivityQuestLookup(Long memberId) {
        return questRepository.myActivityQuestLookup(memberId);
    }

    /**
     * 나의 퀘스트 인증 조회
     * @param memberId  유저의 Id
     * @return 퀘스트 인증 리스트
     */
    public List<MyAuthDto> myActivityAuthPostLookup(Long memberId) {
        return authPostRepository.myActivityAuthPostLookup(memberId);
    }
}
