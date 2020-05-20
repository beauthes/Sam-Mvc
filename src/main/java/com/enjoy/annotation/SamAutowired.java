package com.enjoy.annotation;


import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SamAutowired {
    String value() default "";
}
