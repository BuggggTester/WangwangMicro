package com.example.wangwangmicro.Config.Annotation;

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
