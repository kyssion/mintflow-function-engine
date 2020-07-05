package org.mintflow.vertx.http.exceptrion;

public class MintFlowControllerError extends RuntimeException {
    public MintFlowControllerError(String msg){
        super(msg);
    }
}
