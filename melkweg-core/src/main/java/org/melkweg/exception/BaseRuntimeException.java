package org.melkweg.exception;

/**
 * base runtime Error
 */
public class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException(String code){
        super(code);
    }
}
