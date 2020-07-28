package org.mintflow.sql.annotation;

public @interface TableName {
    String value() default "";
    String schema() default "";
}