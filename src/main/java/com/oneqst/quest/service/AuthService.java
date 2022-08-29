package com.oneqst.quest.service;

import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.AuthPost;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.AuthPostDto;
import com.oneqst.quest.dto.AuthPostUpdateDto;
import com.oneqst.quest.repository.AuthPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthPostRepository authPostRepository;

    /**
     * 퀘스트 인증 포스팅
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
     * 퀘스트 인증 포스팅 수정
     */
    public void updateAuthPost(AuthPost authPost, AuthPostUpdateDto authPostUpdateDto) {
        authPost.setTitle(authPostUpdateDto.getTitle());
        authPost.setContent(authPostUpdateDto.getContent());
        authPost.setPostImage(authPostUpdateDto.getPostImage());
        authPostRepository.save(authPost);
    }

    public void deletePost(AuthPost authPost) {
        authPostRepository.delete(authPost);
    }
}
