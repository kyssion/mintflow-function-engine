package org.mintflow.annotation;

import org.mintflow.handler.HandlerType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface MintFlowHandler {
    String name() default "";
    HandlerType type() default HandlerType.UNDERFIND_HANDLE_SYNC;
}
