package org.galaxy.handle;

import org.galaxy.param.ParamWrapper;

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
