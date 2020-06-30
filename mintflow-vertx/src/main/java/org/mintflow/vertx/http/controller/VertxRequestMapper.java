package org.mintflow.vertx.http.controller;

import io.vertx.core.http.HttpMethod;

public @interface VertxRequestMapper {
    String url() default "";
    HttpMethod[] httpMethod() default {HttpMethod.POST,HttpMethod.GET};
    String process();
}
