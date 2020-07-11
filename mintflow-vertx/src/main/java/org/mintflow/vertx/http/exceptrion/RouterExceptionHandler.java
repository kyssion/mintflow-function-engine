package org.mintflow.vertx.http.exceptrion;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.http.param.ResponseParam;

public interface RouterExceptionHandler {
    ResponseParam handle(ParamWrapper paramWrapper,Exception e);
}
