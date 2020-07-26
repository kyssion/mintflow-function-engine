package org.mintflow.reflection.exception;

import java.lang.reflect.InvocationTargetException;

public class CannotInvokeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Throwable err = null;

    public Throwable getReason() { return err; }

    public CannotInvokeException(String reason) {
        super(reason);
    }

    public CannotInvokeException(InvocationTargetException e) {
        super("by " + e.getTargetException().toString());
        err = e.getTargetException();
    }

    public CannotInvokeException(IllegalAccessException e) {
        super("by " + e.toString());
        err = e;
    }

    public CannotInvokeException(ClassNotFoundException e) {
        super("by " + e.toString());
        err = e;
    }
}