package org.mintflow.vertx.http.adapter.response;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public class ControllerMapperResponseAdapter extends DefaultResponseParamAdapter {

    public Class<?> responseType;

    public ControllerMapperResponseAdapter(Class<?> type){
        this.responseType = type;
    }

    @Override
    public <T> ResponseParam createResponseParams(ParamWrapper paramWrapper, Class<T> type) {
        return super.createResponseParams(paramWrapper, type);
    }
}
