package org.melkweg.handle.sync;

import org.melkweg.handle.HandleType;

import static org.melkweg.handle.HandleType.SAMPLE_HANDLE;

/**
 * Implementation class of abstract classes
 */
public abstract class SyncSampleFnHandler extends SyncFnHandle {

    public SyncSampleFnHandler(String name){
        this(name,SAMPLE_HANDLE);
    }

    private SyncSampleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public SyncSampleFnHandler clone() throws CloneNotSupportedException {
        return (SyncSampleFnHandler) super.clone();
    }

}
