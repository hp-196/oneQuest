package com.oneqst.notice;

import java.util.List;

public interface NoticeRepositoryCustom {
    /**
     * 자신의 모든 알림을 조회한다.
     * @param memberId  유저의 Id
     * @return  해당 유저의 알림 리스트
     */
    List<Long> myTotalNotice(Long memberId);
}
