package com.oneqst.quest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class InviteDto {

    @NotBlank
    private String nickNameOrEmail;

}
