package com.oneqst.quest.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class AuthPostDto {

    @NotBlank
    private String title;

    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime postTime;

    private String postImage;

}
