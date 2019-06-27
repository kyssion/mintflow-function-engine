package com.kyssion.galaxy.annotation;

/**
 * Designation process interface
 */
public @interface ProcessNameSpace {
    String id() default "";
    boolean isAll() default false;
}