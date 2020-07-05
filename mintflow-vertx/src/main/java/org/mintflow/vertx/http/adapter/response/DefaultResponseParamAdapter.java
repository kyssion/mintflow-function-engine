package org.mintflow.vertx.http.adapter.response;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.adapter.request.DefaultRequestParamAdapter;
import org.mintflow.vertx.http.param.ResponseParam;

public class DefaultResponseParamAdapter implements ResponseParamAdapter {

    public Class<?> type;

    public DefaultResponseParamAdapter(){
        this(ResponseParam.class);
    }

    public DefaultResponseParamAdapter(Class<?> type){
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public <T> ResponseParam createResponseParams(ParamWrapper paramWrapper) {
        return paramWrapper.getParam(ResponseParam.class);
    }
}
