package com.kyssion.galaxy.mark;

public enum  Type {
    HANDLE("handle"),PROCESS("process");

    Type(String desc){
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
