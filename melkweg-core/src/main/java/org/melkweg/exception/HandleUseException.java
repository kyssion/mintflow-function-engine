package org.melkweg.exception;


public class HandleUseException extends BaseRuntimeException{
    public static final String NO_USE_SYNC = "当前组件不支持同步方法";
    public static final String NO_USE_ASYNC = "当前组件不支持异步方法";
    public static final String CAN_NOT_USE_SIMPLE_HANDLE_METHOD_FOR_TOOLS_HANDLE="当前工具类组件不能使用 ，不含迭代器的handle方法";
    public HandleUseException(String code) {
        super(code);
    }
}
