package org.melkweg.exception;

/**
 * base runtime Error
 */
public class BaseRuntimeError extends RuntimeException {
    public BaseRuntimeError(String code){
        super(code);
    }
}
