package org.mintflow.vertx.param.adapter;

import org.mintflow.param.ParamWrapper;

public interface ResponseParamAdapter<T> {
    T createResponseParams(ParamWrapper paramWrapper);
}
