package com.kyssion.galaxy.handle.select;

import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

public class SelectorStartHandle implements Handle {

    private List<SelectorHandle> otherSelector;
    private Scheduler scheduler;

    public SelectorStartHandle(){
        this.otherSelector = new ArrayList<>();
    }

    public List<SelectorHandle> getOtherSelector() {
        return otherSelector;
    }

    public void setScheduler(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public ParamWrapper selectHandleSteamRun(ParamWrapper p){
        if(otherSelector==null||scheduler==null){
            return p;
        }
        SelectorHandle selectItem = null;
        for(SelectorHandle selectorHandle : otherSelector){
            if(selectorHandle.select(p)){
                selectItem = selectorHandle;
            }
        }
        if(selectItem==null){
            return p;
        }
        return this.scheduler.run(p,selectItem.getHandleList());
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return this.selectHandleSteamRun(p);
    }
}
