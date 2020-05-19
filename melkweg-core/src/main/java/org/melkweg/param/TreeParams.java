package org.melkweg.param;

public class TreeParams<R> {

    private R returnObject;

    public void set(R value){
        this.returnObject = value;
    }

    public R getReturn(){
        return returnObject;
    }

}
