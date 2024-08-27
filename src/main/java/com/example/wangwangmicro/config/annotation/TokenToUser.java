package com.example.wangwangmicro.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenToUser {
    /*
    return user name
     */
    String value() default "user";
}
