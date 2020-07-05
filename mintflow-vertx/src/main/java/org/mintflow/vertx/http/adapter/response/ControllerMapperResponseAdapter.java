package org.mintflow.vertx.http.adapter.response;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public class ControllerMapperResponseAdapter extends DefaultResponseParamAdapter {

    public ControllerMapperResponseAdapter(Class<?> type){
        super(type);
    }

    @Override
    public <T> ResponseParam createResponseParams(ParamWrapper paramWrapper) {
        if(type==ResponseParam.class){
            return super.createResponseParams(paramWrapper);
        }else{
            T result = type == void.class ? null : paramWrapper.getParam(type);
            ResponseParam responseParam = new ResponseParam();
            responseParam.setData(result);
            responseParam.setStatusCode(200);
            responseParam.setStatusMessage("success");
            return responseParam;
        }
    }
}
