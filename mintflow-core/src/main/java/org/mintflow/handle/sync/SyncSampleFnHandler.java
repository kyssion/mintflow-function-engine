package org.mintflow.handle.sync;

import org.mintflow.handle.HandlerType;

import static org.mintflow.handle.HandlerType.SAMPLE_HANDLE_SYNC;

/**
 * Implementation class of abstract classes
 */
public abstract class SyncSampleFnHandler extends SyncFnHandler {

    public SyncSampleFnHandler(String name){
        this(name, SAMPLE_HANDLE_SYNC);
    }

    private SyncSampleFnHandler(String name, HandlerType handleType) {
        super(name, handleType);
    }

    @Override
    public SyncSampleFnHandler clone() throws CloneNotSupportedException {
        return (SyncSampleFnHandler) super.clone();
    }

}
