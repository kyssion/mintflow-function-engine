package org.galaxy.handle;

import org.galaxy.handle.type.Type;
import org.galaxy.param.ParamWrapper;


public interface Handle {

    default Type getType() {
        return Type.HANDLE;
    }

    default void before() {
    }

    default void after() {
    }

    default void error(Exception e) {
    }

    ParamWrapper handle(ParamWrapper p);
}
