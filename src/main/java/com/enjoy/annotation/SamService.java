package com.enjoy.annotation;


import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SamService {
    String value() default "";
}
