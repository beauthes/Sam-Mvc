package com.enjoy.annotation;


import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SamRequestPara {
    String value() default "";
}
