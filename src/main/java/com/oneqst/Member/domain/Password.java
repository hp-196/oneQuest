package com.oneqst.Member.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Password {

    private String currentPassword;

    @Length(min = 4, max = 30)
    private String newPassword;

    @Length(min = 4, max = 30)
    private String reNewPassword;
}
