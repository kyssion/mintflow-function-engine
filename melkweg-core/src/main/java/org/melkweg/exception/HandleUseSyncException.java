package org.melkweg.exception;


public class HandleUseSyncException extends BaseRuntimeException{
    public static final String NO_USE_SYNC = "当前组件不支持同步方法";
    public static final String NO_USE_ASYNC = "当前组件不支持异步方法";

    public HandleUseSyncException(String code) {
        super(code);
    }
}
