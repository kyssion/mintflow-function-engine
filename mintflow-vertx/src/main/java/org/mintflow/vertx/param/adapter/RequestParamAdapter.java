package org.mintflow.vertx.param.adapter;

import org.mintflow.param.ParamWrapper;

public interface RequestParamAdapter<T> {
    ParamWrapper createParams(T t);
}
