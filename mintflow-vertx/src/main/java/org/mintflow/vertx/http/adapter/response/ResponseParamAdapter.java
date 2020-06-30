package org.mintflow.vertx.http.adapter.response;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public interface ResponseParamAdapter {
    <T> ResponseParam createResponseParams(ParamWrapper paramWrapper, Class<T> type);
}
