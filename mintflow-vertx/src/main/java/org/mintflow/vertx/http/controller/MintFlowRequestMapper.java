package org.mintflow.vertx.http.controller;

import io.vertx.core.http.HttpMethod;
import org.mintflow.vertx.http.adapter.request.ControllerMapperParamAdapter;
import org.mintflow.vertx.http.adapter.request.DefaultRequestParamAdapter;
import org.mintflow.vertx.http.adapter.request.RequestParamAdapter;
import org.mintflow.vertx.http.adapter.response.ControllerMapperResponseAdapter;
import org.mintflow.vertx.http.adapter.response.DefaultResponseParamAdapter;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface MintFlowRequestMapper {
    String url() default "";
    HttpMethod[] httpMethod() default {HttpMethod.POST,HttpMethod.GET};
    String process();
//    Class<? extends ResponseParamAdapter> responseParamAdapter() default DefaultResponseParamAdapter.class;
    Class<? extends RequestParamAdapter> requestParamAdapter() default ControllerMapperParamAdapter.class;
}
