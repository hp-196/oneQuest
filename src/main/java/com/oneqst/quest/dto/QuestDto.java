package com.oneqst.quest.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class QuestDto {

    @NotBlank
    @Length(min = 2, max = 30)
    private String questTitle; //퀘스트 제목

    @NotBlank
    @Length(max = 50)
    private String questIntroduce; //퀘스트 짧은 설명

    @NotBlank
    private String questExplain; //퀘스트 긴 설명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate questStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate questEndTime;

    private String questImage; //퀘스트 대표 이미지

    @NotBlank
    @Length(min = 2, max = 50)
    private String questUrl; //퀘스트 주소

}