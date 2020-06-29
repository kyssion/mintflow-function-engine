package org.mintflow.vertx.param.adapter;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParam;

public class DefaultResponseParamAdapter implements ResponseParamAdapter<ResponseParam> {

    @Override
    public ResponseParam createResponseParams(ParamWrapper paramWrapper) {
        return paramWrapper.getParam(ResponseParam.class);
    }

}
