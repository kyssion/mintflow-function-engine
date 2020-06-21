package org.mintflow.process;

public enum  ProcessType {
    ASYNC("async",1),SYNC("sync",2);

    ProcessType(String name,int code){
        this.name = name;
        this.code = code;
    }

    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
