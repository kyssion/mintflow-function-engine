package org.mintflow.sql.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface TableName {
    String value() default "";
    String schema() default "";
}