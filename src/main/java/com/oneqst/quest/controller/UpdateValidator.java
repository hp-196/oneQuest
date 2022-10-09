package com.oneqst.quest.controller;

import com.oneqst.quest.dto.QuestUpdateDto;
import com.oneqst.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UpdateValidator implements Validator {

    private final QuestRepository questRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(QuestUpdateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QuestUpdateDto questUpdateDto = (QuestUpdateDto) target;

        if (questUpdateDto.getQuestEndTime().isBefore(questUpdateDto.getQuestStartTime())) {
            errors.rejectValue("questEndTime", "날짜 오류", "종료일자를 정확히 입력해주세요.");
        }

        if (!questUpdateDto.getCurrentUrl().equals(questUpdateDto.getQuestUrl()) && questRepository.existsByQuestUrl(questUpdateDto.getQuestUrl())) {
            errors.rejectValue("questUrl", "주소 중복 오류", "이미 존재하는 주소입니다");
        }
    }
}
