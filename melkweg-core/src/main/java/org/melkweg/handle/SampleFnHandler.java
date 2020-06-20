package org.melkweg.handle;

import static org.melkweg.handle.HandleType.SAMPLE_HANDLE;

/**
 * Implementation class of abstract classes
 */
public abstract class SampleFnHandler extends SyncFnHandle {

    public SampleFnHandler(String name){
        this(name,SAMPLE_HANDLE);
    }

    private SampleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public SampleFnHandler clone() throws CloneNotSupportedException {
        return (SampleFnHandler) super.clone();
    }

}
