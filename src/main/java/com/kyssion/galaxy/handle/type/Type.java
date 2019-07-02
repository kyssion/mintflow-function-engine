package com.kyssion.galaxy.handle.type;

public enum Type {

    HANDLE("handle", "handle"),
    REODER_HANDLE("reorder_handle", "reorder_handle"),
    Selector_HANDLE("selector_handle", "selector_handle"),
    IF_HANDLE("if_handle", "if_handle"),
    ELSE_IF_HANDLE("else_if_handle", "else_if_handle"),
    ELSE_HANDLE("else_handle", "else_handle");

    private String desc;
    private String name;

    Type(String name, String desc) {
        this.desc = desc;
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
