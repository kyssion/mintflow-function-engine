package org.mekweg.handle.reoder;

import org.mekweg.handle.Handle;
import org.mekweg.handle.type.Type;
import org.mekweg.param.ParamWrapper;

import java.util.List;

/**
 * 重排序handle
 */
public abstract class ReorderHandle implements Handle {
    public abstract List<Handle> buildHandleStream(List<Handle> handleList, ParamWrapper paramWrapper);

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

    @Override
    public Type getType() {
        return Type.REODER_ITEM;
    }
}
