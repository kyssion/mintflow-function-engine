package org.mekweg.annotation;

import java.lang.annotation.*;

/**
 * Designation process interface
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ProcessNameSpace {
    String id() default "";
}