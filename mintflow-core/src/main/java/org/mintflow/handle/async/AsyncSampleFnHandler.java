package org.mintflow.handle.async;

import org.mintflow.handle.HandleType;

import static org.mintflow.handle.HandleType.SAMPLE_HANDLE_ASYNC;

/**
 * Implementation class of abstract classes
 */
public abstract class AsyncSampleFnHandler extends AsyncFnHandler {

    public AsyncSampleFnHandler(String name){
        this(name, SAMPLE_HANDLE_ASYNC);
    }

    private AsyncSampleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }
}
