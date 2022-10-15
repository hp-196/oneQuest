package com.oneqst.quest.dto;

import com.oneqst.tag.Tag;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

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

    @Lob
    private String questImage; //퀘스트 대표 이미지

    @NotBlank
    @Length(min = 2, max = 50)
    @Pattern(regexp = "[0-9a-zA-Z가-힣]{2,50}", message = "지원하지않는 형식입니다.")
    private String questUrl; //퀘스트 주소

//    @Pattern(regexp = "(#[\\d|A-Z|a-z|가-힣|가-힣]*)", message = "지원하지 않는 형식입니다. 단어 형태로 입력해주세요.")
    private String tag; //태그 이름

}