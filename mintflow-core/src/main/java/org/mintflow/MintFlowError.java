package org.mintflow;

import org.mintflow.param.ParamWrapper;

public class MintFlowError {
    private Exception exception;
    private ParamWrapper paramWrapper;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public ParamWrapper getParamWrapper() {
        return paramWrapper;
    }

    public void setParamWrapper(ParamWrapper paramWrapper) {
        this.paramWrapper = paramWrapper;
    }
}
