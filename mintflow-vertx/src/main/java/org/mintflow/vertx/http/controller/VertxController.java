package org.mintflow.vertx.http.controller;

public @interface VertxController {
    String url() default "";
    String nameSpace();
}
