package org.galaxy.handle.reoder;

import org.galaxy.handle.Handle;
import org.galaxy.handle.type.Type;
import org.galaxy.param.ParamWrapper;

import java.util.List;

/**
 * 重排序handle
 */
public abstract class ReorderHandle implements Handle {


    public abstract void buildHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper);

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return p;
    }

    @Override
    public Type getType() {
        return Type.REODER_ITEM;
    }
}
