package org.mintflow.vertx.param.adapter;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParams;

public interface ResponseParamAdapter<T> {
    T createResponseParams(ParamWrapper paramWrapper);
}
