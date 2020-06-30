package org.mintflow.vertx.http.adapter.response;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public class DefaultResponseParamAdapter implements ResponseParamAdapter {

    @Override
    public <T> ResponseParam createResponseParams(ParamWrapper paramWrapper,Class<T> type) {
        T result = paramWrapper.getParam(type);
        ResponseParam responseParam = new ResponseParam();
        responseParam.setData(result);
        responseParam.setStatusCode(200);
        responseParam.setStatusMessage("success");
        return responseParam;
    }
}
