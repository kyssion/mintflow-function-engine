package org.melkweg.handle;

import org.melkweg.param.ParamWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class CycleHandler extends Handler {
    private List<Handler> cycleHandlerList = new ArrayList<>();
    protected CycleHandler(String name) {
        this(name,HandleType.CYCLE_HANDLE);
    }

    private CycleHandler(String name, HandleType handleType) {
        super(name, handleType);
    }

    protected ParamWrapper cycleHandle(ParamWrapper paramWrapper){
        return this.getScheduler().run(paramWrapper,cycleHandlerList);
    }

    public void setCycleHandlerList(List<Handler> cycleHandlerList) {
        this.cycleHandlerList = cycleHandlerList;
    }

    @Override
    public CycleHandler clone() throws CloneNotSupportedException {
        CycleHandler cycleHandler = (CycleHandler)super.clone();
        cycleHandler.cycleHandlerList = new ArrayList<>();
        return cycleHandler;
    }
}
