package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.Score;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.dto.AuthPostUpdateDto;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthPostRepository authPostRepository;
    private final ScoreRepository scoreRepository;

    /**
     * 인증 포스팅 생성
     */
    public AuthPost AuthPost(AuthPostDto authPostDto, Quest quest, Member member) {
        AuthPost authPost = AuthPost.builder()
                .title(authPostDto.getTitle())
                .content(authPostDto.getContent())
                .postTime(LocalDateTime.now())
                .postImage(authPostDto.getPostImage())
                .writer(member)
                .quest(quest)
                .build();
        AuthPost newAuthPost = authPostRepository.save(authPost);
        return newAuthPost;
    }

    /**
     * 인증 포스팅 수정
     */
    public void updateAuthPost(AuthPost authPost, AuthPostUpdateDto authPostUpdateDto) {
        authPost.setTitle(authPostUpdateDto.getTitle());
        authPost.setContent(authPostUpdateDto.getContent());
        authPost.setPostImage(authPostUpdateDto.getPostImage());
        authPostRepository.save(authPost);
    }

    /**
     * 인증 포스팅 삭제
     */
    public void deletePost(AuthPost authPost) {
        authPostRepository.delete(authPost);
    }

    public Score plusScore(Member member, AuthPost authPost, Quest quest, int sc) {
        Score score = Score.builder()
                .member(member)
                .authPost(authPost)
                .quest(quest)
                .score(sc)
                .build();
        Score newScore = scoreRepository.save(score);
        authPost.setConfirm(true);
        return newScore;
    }

    public int countScore(List<Score> scoreList, Member member, int a) {
        for (Score score : scoreList) {
            if (score.getMember().equals(member)) {
                a+=score.getScore();
            }
        }
        return a;
    }
}
