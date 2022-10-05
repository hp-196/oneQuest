package com.oneqst.Member.service;

import com.oneqst.quest.dto.MyCommentDto;
import com.oneqst.quest.dto.MyQuestPostDto;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
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
    private final QuestPostRepository questPostRepository;
    private final CommentRepository commentRepository;

    /**
     *나의 포스트 조회
     * @param memberId  유저의 id
     * @return  포스트 리스트
     */
    public List<MyQuestPostDto> myQuestPostLookup(Long memberId) {
        return questPostRepository.myQuestPostLookup(memberId);
    }

    /**
     * 나의 댓글 조회
     * @param memberId 유저의 id
     * @return  댓글 리스트
     */
    public List<MyCommentDto> myCommentLookup(Long memberId) {
        return commentRepository.myCommentLookup(memberId);
    }
}
