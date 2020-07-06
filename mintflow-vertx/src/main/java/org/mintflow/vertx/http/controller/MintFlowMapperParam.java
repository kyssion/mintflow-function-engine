package org.mintflow.vertx.http.controller;

import org.mintflow.vertx.http.adapter.request.ControllerMapperParamAdapter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Inherited
public @interface MintFlowMapperParam {
    String fromName() default "";
    String toName() default "";
    ControllerMapperParamAdapter.RuleType fromType() default ControllerMapperParamAdapter.RuleType.FROM_PARAMS;
}
