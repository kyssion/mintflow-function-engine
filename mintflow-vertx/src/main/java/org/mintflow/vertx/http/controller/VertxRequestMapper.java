package org.mintflow.vertx.http.controller;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface VertxRequestMapper {
    String url() default "";
    HttpMethod[] httpMethod() default {HttpMethod.POST,HttpMethod.GET};
    String process();
}
