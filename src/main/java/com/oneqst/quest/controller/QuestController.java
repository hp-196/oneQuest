package com.oneqst.quest.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneqst.Member.controller.CurrentUser;
import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import com.oneqst.quest.dto.QuestDto;
import com.oneqst.quest.repository.QuestRepository;
import com.oneqst.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;
    private final QuestRepository questRepository;

    /**
    @InitBinder
    public void initBinder(WebDataBinder binder){
     binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
            } **/
    /**
     * 퀘스트 생성 페이지
     */
    @GetMapping("/new-quest")
    public String newQuest(@CurrentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute(new QuestDto());
        return "new-quest";
    }

    /**
     * 퀘스트 생성 POST
     */
    @PostMapping("/new-quest")
    public String newQuestPost(@CurrentUser Member member, @Valid QuestDto questDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info(String.valueOf(errors));
            return "new-quest";
        }
        Member newMember = member;
        Quest newQuest = questService.newQuest(questDto, newMember);
        return "redirect:/quest/" + newQuest.getQuestUrl();
    }

    @GetMapping("/quest/{url}")
    public String questView(@CurrentUser Member member, @PathVariable String url, Model model) {
        model.addAttribute(member);
        model.addAttribute(questRepository.findByQuestUrl(url));
        log.info(String.valueOf(model));
        return "quest-view";

    }
}
