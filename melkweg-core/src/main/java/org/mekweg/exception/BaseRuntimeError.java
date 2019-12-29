package org.mekweg.exception;

public class BaseRuntimeError extends RuntimeException {
    public BaseRuntimeError(String code){
        super(code);
    }
}
