package com.kyssion.galaxy.handle;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

public class StartHandler implements Handle {

    private List<Handle> handlerList;

    public StartHandler(){
        this.handlerList = new ArrayList<>();
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return null;
    }

    public void addHandle(Handle handle){
        this.handlerList.add(handle);
    }

    public List<Handle> getHandleList (){
        return this.handlerList;
    }
}
