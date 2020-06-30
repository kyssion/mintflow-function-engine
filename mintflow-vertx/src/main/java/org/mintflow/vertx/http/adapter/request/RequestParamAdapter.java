package org.mintflow.vertx.http.adapter.request;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.RequestParam;

public interface RequestParamAdapter {
    ParamWrapper createParams(RequestParam t);
}
