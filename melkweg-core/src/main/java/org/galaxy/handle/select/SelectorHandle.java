package org.galaxy.handle.select;

import org.galaxy.handle.Handle;
import org.galaxy.handle.type.Type;
import org.galaxy.param.ParamWrapper;

/**
 * 简单选择器
 */
public abstract class SelectorHandle implements Handle {

    public abstract boolean select(ParamWrapper p);

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

    @Override
    public Type getType() {
        return Type.SELEECT_ITEM;
    }
}
