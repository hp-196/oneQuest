package com.oneqst.Member.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberDto {

    @NotBlank
    @Length(min = 2, max = 30)
    private String nickname;

    @NotBlank
    @Length(min = 4, max = 30)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;


}
