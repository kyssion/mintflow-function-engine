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

    List<SelectorHandle> otherSelect;

    public void setHandleList(List<Handle> handleList) {
        this.handleList = handleList;
    }

    public void setOtherSelect(List<SelectorHandle> otherSelects) {
        this.otherSelect = otherSelects;
    }

    public abstract boolean select(ParamWrapper p);

    public ParamWrapper selectHandle(ParamWrapper p) {
        if (handleList == null || otherSelect == null) {
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
