package org.mekweg.annotation;

import java.lang.annotation.*;

/**
 * Designation handle item
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Handler{
    String value() default "";
}
