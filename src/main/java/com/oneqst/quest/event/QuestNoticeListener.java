package com.oneqst.quest.event;

import com.oneqst.notice.Notice;
import com.oneqst.notice.NoticeRepository;
import com.oneqst.notice.NoticeType;
import com.oneqst.quest.domain.Comment;
import com.oneqst.quest.domain.QuestPost;
import com.oneqst.quest.repository.CommentRepository;
import com.oneqst.quest.repository.QuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Async
@Transactional
@RequiredArgsConstructor
public class QuestNoticeListener {

    private final NoticeRepository noticeRepository;
    private final QuestPostRepository questPostRepository;


    /**
     * 댓글 알림
     */
    @EventListener
    public void commentPostEvent(CommentNotice commentNotice) {
        Comment comment = commentNotice.getComment();
        QuestPost questPost = questPostRepository.findById(commentNotice.getQuestPost().getId()).get();
        Notice notice = new Notice();
        notice.setTitle(questPost.getTitle());
        notice.setMember(comment.getWriter());
        notice.setContent(comment.getContent());
        notice.setNoticeTime(LocalDateTime.now());
        notice.setChecked(false);
        notice.setUrl("/quest/" + questPost.getQuest().getQuestUrl() + "/post/" + questPost.getId());
        notice.setNoticeType(NoticeType.POST_COMMENT);
        noticeRepository.save(notice);
    }
}
