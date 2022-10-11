package com.oneqst.quest.dto;


import com.oneqst.quest.domain.Quest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
    @Pattern(regexp = "[0-9a-zA-Z가-힣]{2,50}", message = "지원하지않는 형식입니다.")
    private String questUrl; //퀘스트 주소

    private String currentUrl; //현재 퀘스트 주소

    @Lob
    private String questImage; //퀘스트 대표 이미지

    public QuestUpdateDto(Quest quest) {
        this.questTitle = quest.getQuestTitle();
        this.questIntroduce = quest.getQuestIntroduce();
        this.questExplain = quest.getQuestExplain();
        this.questStartTime = quest.getQuestStartTime();
        this.questEndTime = quest.getQuestEndTime();
        this.questUrl = quest.getQuestUrl();
        this.currentUrl = quest.getQuestUrl();
        this.questImage = quest.getQuestImage();
    }
}