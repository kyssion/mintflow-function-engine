package org.mekweg.handle.select;

import org.mekweg.handle.Handle;
import org.mekweg.handle.type.Type;
import org.mekweg.param.ParamWrapper;

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
