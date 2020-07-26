package org.mintflow.reflection.wrapper;


import org.mintflow.reflection.MirrorObject;

public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MirrorObject mirrorObject, Object object);

}
