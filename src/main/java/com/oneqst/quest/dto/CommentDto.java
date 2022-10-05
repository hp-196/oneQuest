package com.oneqst.quest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDto {

    @NotBlank
    private String content;
}
