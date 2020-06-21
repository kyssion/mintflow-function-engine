package org.mintflow.annotation;

import org.mintflow.handle.HandleType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface MintFlowHandler {
    String name() default "";
    HandleType type() default HandleType.UNDERFIND_HANDLE_SYNC;
}
