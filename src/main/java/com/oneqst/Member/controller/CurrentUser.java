package com.oneqst.Member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member")
@Target(ElementType.PARAMETER)
public @interface CurrentUser {
    /**
     * https://freedeveloper.tistory.com/217?category=809246 참고
     */
}
