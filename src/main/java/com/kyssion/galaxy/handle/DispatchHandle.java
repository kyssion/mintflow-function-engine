package com.kyssion.galaxy.handle;

import com.kyssion.galaxy.param.ParamWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchHandle implements Handle {

    private Map<String,Handle> handleMap;

    private List<Handle> reorderHandleList;

    public DispatchHandle(){
        this.handleMap = new HashMap<>();
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return null;
    }
}
