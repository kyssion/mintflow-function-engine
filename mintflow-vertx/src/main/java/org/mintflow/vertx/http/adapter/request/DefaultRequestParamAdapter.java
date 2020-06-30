package org.mintflow.vertx.http.adapter.request;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.adapter.response.ResponseParamAdapter;
import org.mintflow.vertx.http.param.RequestParam;
import org.mintflow.vertx.http.param.ResponseParam;

public class DefaultRequestParamAdapter implements RequestParamAdapter {

    @Override
    public ParamWrapper createParams(RequestParam t) {
        ParamWrapper paramWrapper = new ParamWrapper();
        paramWrapper.setParam(t);
        return paramWrapper;
    }
}
