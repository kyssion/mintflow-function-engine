package com.kyssion.galaxy.handle.select;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.List;

/**
 * 简单选择器
 */
public abstract class SelectorHandle implements Handle {

    List<Handle> handleList;

    public void setHandleList(List<Handle> handleList) {
        this.handleList = handleList;
    }

    public List<Handle> getHandleList() {
        return handleList;
    }

    public abstract boolean select(ParamWrapper p);

    public ParamWrapper selectHandle(ParamWrapper p) {
        if (handleList == null) {
            return p;
        }
        return p;
    }


    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return null;
    }

    public Type getType() {
        return Type.Selector_HANDLE;
    }

}
