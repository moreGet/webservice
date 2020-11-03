package com.bns.onm.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 메소드 파라메터 객체만 사용 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    
}
