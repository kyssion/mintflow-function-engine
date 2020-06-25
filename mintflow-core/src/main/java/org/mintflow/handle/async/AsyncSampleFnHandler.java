package org.mintflow.handle.async;

import org.mintflow.handle.HandlerType;

import static org.mintflow.handle.HandlerType.SAMPLE_HANDLE_ASYNC;

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
