package com.oneqst.quest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

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

    private String questStartTime;

    private String questEndTime;

    private String questImage; //퀘스트 대표 이미지

    @NotBlank
    @Length(min = 2, max = 50)
    private String questUrl; //퀘스트 주소

}
