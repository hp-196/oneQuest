package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.domain.Score;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.dto.AuthPostUpdateDto;
import com.oneqst.quest.event.ScoreNotice;
import com.oneqst.quest.repository.AuthPostRepository;
import com.oneqst.quest.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthPostRepository authPostRepository;
    private final ScoreRepository scoreRepository;
    private final ApplicationEventPublisher eventPublisher;

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
                .confirm(false)
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

    /**
     * 인증 포스팅 전체 삭제
     */
    public void deleteAllPost(Member member) {
        List<AuthPost> authPostList = authPostRepository.findByWriter(member);
        authPostRepository.deleteAll(authPostList);
    }

    /**
     * 인증 포스팅 점수 추가
     */
    public Score plusScore(Member member, AuthPost authPost, Quest quest, int sc) {
        Score score = Score.builder()
                .member(member)
                .authPost(authPost)
                .quest(quest)
                .score(sc)
                .build();
        Score newScore = scoreRepository.save(score);
        authPost.setConfirm(true);
        eventPublisher.publishEvent(new ScoreNotice(member, authPost, quest, sc));
        return newScore;
    }

    /**
     * 랭킹 뷰 점수 계산
     */
    public List<Map.Entry<Member, Integer>> countScore(List<Score> scoreList, List<Member> memberList) {

        Map<Member, Integer> rating = new HashMap<>();
        for (Member member : memberList) {
            int i = 0;
            for (Score score : scoreList) {
                if (member.equals(score.getMember())) {
                    i += 5;
                }
            }
            rating.put(member, i);
        }
        List<Map.Entry<Member, Integer>> list = new ArrayList<>(rating.entrySet());
        list.sort(((o1, o2) -> rating.get(o2.getKey()) - rating.get(o1.getKey())));

        return list;
    }
}
