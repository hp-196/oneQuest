package com.oneqst.notice;

import com.oneqst.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByMemberAndCheckedOrderByNoticeTimeDesc(Member member, boolean checked);

    List<Notice> findByMemberOrderByNoticeTimeDesc(Member member);

    int countByCheckedAndMember(boolean checked, Member member);
}
