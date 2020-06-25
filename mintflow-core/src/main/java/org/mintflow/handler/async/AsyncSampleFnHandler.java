package org.mintflow.handler.async;

import org.mintflow.handler.HandlerType;

import static org.mintflow.handler.HandlerType.SAMPLE_HANDLE_ASYNC;

/**
 * Implementation class of abstract classes
 */
public abstract class AsyncSampleFnHandler extends AsyncFnHandler {

    public AsyncSampleFnHandler(String name){
        this(name, SAMPLE_HANDLE_ASYNC);
    }

    private AsyncSampleFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }
}
