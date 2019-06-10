package com.kyssion.galaxy.annotation;

/**
 * Designation process interface
 */
public @interface Processer {
    String id() default "";
    boolean isAll() default false;
}