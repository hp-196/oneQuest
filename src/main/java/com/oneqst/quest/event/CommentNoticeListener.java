package com.oneqst.quest.event;

import com.oneqst.quest.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Async
@Transactional
public class CommentNoticeListener {
    
    @EventListener
    public void commentPostEvent(CommentNotice commentNotice) {
        Comment comment = commentNotice.getComment();
        log.info(comment.getContent());
        log.info(String.valueOf(comment.getWriter()));

    }
}
