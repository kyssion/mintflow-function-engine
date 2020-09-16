package org.mintflow.annotation;

import org.mintflow.process.ProcessType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MintFlowProcess {
    String name();
    ProcessType type() default ProcessType.SYNC;
}
