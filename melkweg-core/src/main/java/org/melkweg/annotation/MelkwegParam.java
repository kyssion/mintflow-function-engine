package org.melkweg.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Inherited
public @interface MelkwegParam {
    String key();
}
