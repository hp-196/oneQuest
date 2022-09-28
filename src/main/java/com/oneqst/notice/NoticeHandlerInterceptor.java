package com.oneqst.notice;

import com.oneqst.Member.controller.MemberInfo;
import com.oneqst.Member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class NoticeHandlerInterceptor implements HandlerInterceptor {

    private final NoticeRepository noticeRepository;

    /**
     * https://kyu9341.github.io/java/2020/06/19/java_springBootInceptor/ 참고
     * 뷰 랜더링 전 알림들을 추가해줌
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && authentication != null && authentication.getPrincipal() instanceof MemberInfo) {
            Member member = ((MemberInfo)authentication.getPrincipal()).getMember();

            modelAndView.addObject("noticeList", noticeRepository.findByMemberAndCheckedOrderByNoticeTimeDesc(member, false));
            modelAndView.addObject("newNoticeCount", noticeRepository.countByCheckedAndMember(false, member));
        }
    }
}
