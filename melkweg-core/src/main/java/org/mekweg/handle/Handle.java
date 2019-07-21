package org.mekweg.handle;

import org.mekweg.handle.type.Type;
import org.mekweg.param.ParamWrapper;


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
