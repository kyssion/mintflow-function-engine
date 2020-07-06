package org.mintflow.vertx.http.adapter.request;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.RequestParam;

import java.lang.reflect.Method;

public interface RequestParamAdapter {
    void initAdapter(Method method);
    ParamWrapper createParams(RequestParam t);
}
