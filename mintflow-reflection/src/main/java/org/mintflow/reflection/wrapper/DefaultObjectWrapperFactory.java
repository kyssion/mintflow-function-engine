package org.mintflow.reflection.wrapper;

import org.mintflow.reflection.exception.ReflectionException;
import org.mintflow.reflection.MirrorObject;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MirrorObject mirrorObject, Object object) {
        throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}