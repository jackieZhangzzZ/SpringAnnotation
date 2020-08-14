package com.yc.springframework.context.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

}
