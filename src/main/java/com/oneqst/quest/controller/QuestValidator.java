package com.oneqst.quest.controller;

import com.oneqst.quest.dto.QuestDto;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class QuestValidator implements Validator {

    private final QuestRepository questRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(QuestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QuestDto questDto = (QuestDto) target;

        if (questDto.getQuestEndTime().isBefore(questDto.getQuestStartTime())) {
            errors.rejectValue("questEndTime", "날짜 오류", "종료일자를 정확히 입력해주세요.");
        }

        if (questRepository.existsByQuestUrl(questDto.getQuestUrl())) {
            errors.rejectValue("questUrl", "주소 중복 오류", "이미 존재하는 주소입니다.");
        }
    }
}
