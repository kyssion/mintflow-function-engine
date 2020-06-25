package org.mintflow.param;

public class CycleParam {

    private ParamWrapper startParamWrapper;
    private int cycleTimes;

    public CycleParam(){
        super();
    }

    public CycleParam(int times,ParamWrapper startParamWrapper){
        this.cycleTimes = times;
        this.startParamWrapper = startParamWrapper;
    }

    public ParamWrapper getStartParamWrapper() {
        return startParamWrapper;
    }

    public void setStartParamWrapper(ParamWrapper startParamWrapper) {
        this.startParamWrapper = startParamWrapper;
    }

    public int getCycleTimes() {
        return cycleTimes;
    }

    public void setCycleTimes(int cycleTimes) {
        this.cycleTimes = cycleTimes;
    }
}
