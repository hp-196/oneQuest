package com.oneqst.quest.dto;


import com.oneqst.Member.domain.Member;
import com.oneqst.quest.domain.Quest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestUpdateDto {

    @Length(min = 2, max = 30)
    private String questTitle; //퀘스트 제목

    @Length(max = 50)
    private String questIntroduce; //퀘스트 짧은 설명

    private String questExplain; //퀘스트 긴 설명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate questStartTime; //퀘스트 시작 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate questEndTime; //퀘스트 종료 시간

    @Length(min = 2, max = 50)
    private String questUrl; //퀘스트 주소

    private String questImage; //퀘스트 대표 이미지

    public QuestUpdateDto(Quest quest) {
        this.questTitle = quest.getQuestTitle();
        this.questIntroduce = quest.getQuestIntroduce();
        this.questExplain = quest.getQuestExplain();
        this.questStartTime = quest.getQuestStartTime();
        this.questEndTime = quest.getQuestEndTime();
        this.questUrl = quest.getQuestUrl();
        this.questImage = quest.getQuestImage();
    }
}