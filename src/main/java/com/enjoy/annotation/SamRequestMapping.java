package com.enjoy.annotation;


import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface SamRequestMapping {
    String value() default "";
}
