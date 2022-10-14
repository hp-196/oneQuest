package com.oneqst.quest.event;

import com.oneqst.Member.domain.Member;
import com.oneqst.Member.repository.MemberRepository;
import com.oneqst.config.AlertMessage;
import com.oneqst.quest.domain.JoinApplication;
import com.oneqst.quest.domain.JoinType;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.repository.JoinApplicationRepository;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JoinApplicationService {

    private final JoinApplicationRepository joinApplicationRepository;
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final QuestService questService;

    /**
     * 새로운 신청
     */
    public void newWaitingMember(Quest quest, Member joinMember) {
        JoinApplication joinApplication = JoinApplication.builder()
                .quest(quest)
                .member(joinMember)
                .time(LocalDateTime.now())
                .accepted(false)
                .build();
        joinApplicationRepository.save(joinApplication);
    }

    /**
     * 신청 관리 페이지 접근
     */
    public void goPage(Member member, String url, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        List<JoinApplication> list = joinApplicationRepository.findByQuest(quest);
        model.addAttribute(member);
        model.addAttribute(quest);
        model.addAttribute("applicationList",list);
    }

    /**
     * 퀘스트 가입 상태 변경 일반<->신청후대기
     * 신청 후 대기 상태에서 일반으로 변경시 기존 대기상태였던 인원들도 전부 가입 처리
     */
    public void stateChange(Member member, String url) {
        Quest quest = questRepository.findByQuestUrl(url);
        List<JoinApplication> applicationList = joinApplicationRepository.findByQuest(quest);
        if (!quest.getQuestHost().equals(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        if (quest.getJoinType() == JoinType.NORMAL) {
            quest.setJoinType(JoinType.WAITING);
        } else {
            quest.setJoinType(JoinType.NORMAL);
            for (JoinApplication app : applicationList) {
                questService.addQuestMember(app.getQuest(), app.getMember());
                joinApplicationRepository.delete(app);
            }
        }
    }

    /**
     * 가입 신청
     * 신청한 인원 중복 검사 후 새로운 신청 처리
     */
    public String joinApplication(Member member, String url, String nickname, Model model) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (quest.isQuestRecruitEnd() == false) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        if (joinApplicationRepository.findByQuestAndMember(quest, member) != null) {
            model.addAttribute("data", new AlertMessage("신청 대기중입니다. 신청이 완료되면 알림을 보내드립니다.", "/quest/"+ url));
            return "alertMessage";
        }
        Member joinMember = memberRepository.findByNickname(nickname);
        newWaitingMember(quest, joinMember);
        model.addAttribute("data", new AlertMessage("가입 신청이 완료되었습니다. 수락이 완료되면 알림을 보내드립니다.", "/quest/"+ url));
        return null;
    }


    /**
     * 신청 수락
     */
    public void acceptApplication(Member member, String url, String nickname) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        Member joinMember = memberRepository.findByNickname(nickname);
        JoinApplication joinApplication = joinApplicationRepository.findByQuestAndMember(quest, joinMember);
        questService.addQuestMember(quest, joinMember);
        joinApplicationRepository.delete(joinApplication);
    }

    /**
     * 신청 거절
     */
    public void cancelApplication(Member member, String url, String nickname) {
        Quest quest = questRepository.findByQuestUrl(url);
        if (!quest.isHostOrMaster(member)) {
            throw new IllegalArgumentException(member.getNickname()+"가 "+ quest +"로 불건전한 접근 시행");
        }
        Member joinMember = memberRepository.findByNickname(nickname);
        JoinApplication joinApplication = joinApplicationRepository.findByQuestAndMember(quest,joinMember);
        joinApplicationRepository.delete(joinApplication);
    }
}
