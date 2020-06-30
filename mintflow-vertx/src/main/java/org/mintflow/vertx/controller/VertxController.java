package org.mintflow.vertx.controller;

public @interface VertxController {
    String url() default "";
    String nameSpace();
}
