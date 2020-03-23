package org.mekweg.param;

public class TreeParams {
    private Object returnObject;

    public void set(Object value){
        this.returnObject = value;
    }

    @SuppressWarnings("unchecked")
    public <R> R getReturn(){
        return (R) returnObject;
    }
}
