package org.mintflow.vertx.http.exceptrion;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public class DefaultRouterExceptionHandler implements RouterExceptionHandler {
    @Override
    public ResponseParam handle(ParamWrapper paramWrapper, Exception e) {
        ResponseParam responseParam = new ResponseParam();
        responseParam.setStatusCode(500);
        responseParam.setStatusMessage("error");
        responseParam.setData(e.getStackTrace());
        return responseParam;
    }
}
