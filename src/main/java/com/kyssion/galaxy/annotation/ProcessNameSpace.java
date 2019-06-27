package com.kyssion.galaxy.annotation;

import java.lang.annotation.*;

/**
 * Designation process interface
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ProcessNameSpace {
    String id() default "";
    boolean isAll() default false;
}