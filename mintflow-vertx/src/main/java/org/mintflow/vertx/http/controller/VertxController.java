package org.mintflow.vertx.http.controller;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface VertxController {
    String url() default "";
    String nameSpace();
}
