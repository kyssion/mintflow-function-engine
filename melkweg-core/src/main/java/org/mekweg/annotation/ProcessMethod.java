package org.mekweg.annotation;

import java.lang.annotation.*;

/**
 * Designation processMethod
 * defaule all method
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProcessMethod {
    String id() default "";
}
