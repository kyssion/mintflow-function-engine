package org.melkweg.handle;

import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class CycleFnHandler extends FnHandler {
    private List<FnHandler> cycleFnHandlerList = new ArrayList<>();
    protected CycleFnHandler(String name) {
        this(name,HandleType.CYCLE_HANDLE);
    }

    private CycleFnHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    protected ParamWrapper cycleHandle(ParamWrapper paramWrapper){
        return this.getScheduler().run(paramWrapper, cycleFnHandlerList);
    }

    public void setCycleFnHandlerList(List<FnHandler> cycleFnHandlerList) {
        this.cycleFnHandlerList = cycleFnHandlerList;
    }

    @Override
    public CycleFnHandler clone() throws CloneNotSupportedException {
        CycleFnHandler cycleHandler = (CycleFnHandler)super.clone();
        cycleHandler.cycleFnHandlerList = new ArrayList<>();
        return cycleHandler;
    }
}
