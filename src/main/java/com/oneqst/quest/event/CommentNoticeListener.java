package com.oneqst.quest.event;

import com.oneqst.notice.Notice;
import com.oneqst.notice.NoticeRepository;
import com.oneqst.notice.NoticeType;
import com.oneqst.quest.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@Async
@Transactional
@RequiredArgsConstructor
public class CommentNoticeListener {

    private final NoticeRepository noticeRepository;

    @EventListener
    public void commentPostEvent(CommentNotice commentNotice) {
        Comment comment = commentNotice.getComment();
        Notice notice = new Notice();
        notice.setMember(comment.getWriter());
        notice.setContent(comment.getContent());
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setUrl("/quest/" + comment.getPost().getQuest().getQuestUrl() + "/post/" + comment.getPost().getId());
        notice.setNoticeType(NoticeType.POST_COMMENT);
        noticeRepository.save(notice);
    }
}
