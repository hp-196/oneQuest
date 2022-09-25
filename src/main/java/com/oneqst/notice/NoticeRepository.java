package com.oneqst.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
