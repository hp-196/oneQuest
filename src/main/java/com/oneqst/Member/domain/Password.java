package com.oneqst.Member.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Password {

    private String currentPassword;

    @Length(min = 4, max = 30, message = "비밀번호는 4~30글자 사이여야합니다.")
    private String newPassword;

    @Length(min = 4, max = 30, message = "비밀번호는 4~30글자 사이여야합니다.")
    private String reNewPassword;
}
