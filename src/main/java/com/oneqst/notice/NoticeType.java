package com.oneqst.notice;

public enum NoticeType {
    /**
     * 알림 타입은
     * 1.퀘스트에 초대받을경우
     * 2.퀘스트 인증이 완료될경우
     * 3.퀘스트 포스팅에 댓글이 달릴경우
     * 4.공지사항 포스팅시
     * 5.가입신청 승인이 완료됬을경우
     * 6.가입신청 승인이 거절됬을경우
     */

    QUEST_INVITE, QUEST_AUTH, POST_COMMENT, NOTICE_POSTING, JOIN_WAITING, JOIN_CANCEL
}
