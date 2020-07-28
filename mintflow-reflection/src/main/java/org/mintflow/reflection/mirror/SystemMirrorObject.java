package org.mintflow.reflection.mirror;

import org.mintflow.reflection.DefaultReflectorFactory;
import org.mintflow.reflection.MirrorObject;
import org.mintflow.reflection.object.DefaultObjectFactory;
import org.mintflow.reflection.object.ObjectFactory;
import org.mintflow.reflection.wrapper.DefaultObjectWrapperFactory;
import org.mintflow.reflection.wrapper.ObjectWrapperFactory;

public final class SystemMirrorObject {

    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final MirrorObject NULL_META_OBJECT = MirrorObject.forObject(NullObject.class);

    private SystemMirrorObject() {
        // Prevent Instantiation of Static Class
    }

    private static class NullObject {
    }

    public static MirrorObject forObject(Object object) {
        return MirrorObject.forObject(object);
    }

}