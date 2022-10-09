package com.oneqst.Member.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberDto {

    @NotBlank
    @Length(min = 2, max = 30)
    @Pattern(regexp = "[0-9a-zA-Z가-힣]{2,50}", message = "지원하지않는 형식입니다.")
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
