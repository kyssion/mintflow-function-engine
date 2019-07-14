package org.galaxy.handle.select;

import org.galaxy.handle.Handle;
import org.galaxy.handle.type.Type;
import org.galaxy.param.ParamWrapper;
import org.galaxy.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

public class SelectorStartHandle implements Handle {

    private List<SelectorHandle> otherSelector;
    private List<List<Handle>> selectorItemList;
    private Scheduler scheduler;

    public SelectorStartHandle(){
        this.otherSelector = new ArrayList<>();
        selectorItemList = new ArrayList<>();
    }

    public List<List<Handle>> getSelectorItemList() {
        return selectorItemList;
    }

    public void setSelectorItemList(List<List<Handle>> selectorItemList) {
        this.selectorItemList = selectorItemList;
    }

    public List<SelectorHandle> getOtherSelector() {
        return otherSelector;
    }

    public void setScheduler(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public ParamWrapper selectHandleSteamRun(ParamWrapper p){
        if(otherSelector==null||scheduler==null||this.otherSelector.size()!=this.getSelectorItemList().size()){
            return p;
        }
        SelectorHandle selectItem = null;
        int index = 0;
        for(SelectorHandle selectorHandle : otherSelector){
            if(selectorHandle.select(p)){
                selectItem = selectorHandle;
                break;
            }
            index++;
        }
        if(selectItem==null){
            return p;
        }
        return this.scheduler.run(p,this.selectorItemList.get(index));
    }

    @Override
    public Type getType() {
        return Type.Selector_HANDLE;
    }

    @Override
    public ParamWrapper handle(ParamWrapper p) {
        return this.selectHandleSteamRun(p);
    }
}
