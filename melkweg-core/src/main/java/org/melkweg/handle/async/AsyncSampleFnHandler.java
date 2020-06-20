package org.melkweg.handle.async;

import org.melkweg.handle.HandleType;

import static org.melkweg.handle.HandleType.SAMPLE_HANDLE_SYNC;

/**
 * Implementation class of abstract classes
 */
public abstract class AsyncSampleFnHandler extends AsyncFnHandle {

    public AsyncSampleFnHandler(String name){
        this(name, SAMPLE_HANDLE_SYNC);
    }

    private AsyncSampleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public AsyncSampleFnHandler clone() throws CloneNotSupportedException {
        return (AsyncSampleFnHandler) super.clone();
    }

}
