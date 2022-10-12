package com.oneqst.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void readOne(Notice notice) {
        notice.setChecked(true);
    }

    public void deleteTotalNotice(Long memberId) {
        List<Long> ids = noticeRepository.myTotalNotice(memberId);
        for (Long id : ids) {
            noticeRepository.deleteById(id);
        }
    }
}
