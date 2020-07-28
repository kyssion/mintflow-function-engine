package org.mintflow.sql.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface TableField {
    String value() default "";
    boolean exist() default true;

}
