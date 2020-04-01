package org.melkweg.annotation;

import org.melkweg.handle.HandleType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface MelkwegHander {
    String name() default "";
    HandleType type() default HandleType.SAMPLE_HANDLE;
}
