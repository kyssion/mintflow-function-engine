package org.mekweg.param;

public class TreeParams<R> {
    private Object returnObject;

    public void set(Object value){
        this.returnObject = value;
    }

    @SuppressWarnings("unchecked")
    public R getReturn(){
        return (R) returnObject;
    }
}
