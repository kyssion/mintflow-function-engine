package org.mekweg.handle.type;

public enum Type {

    HANDLE("handle", "handle"),
    REODER_ITEM("reoder_item","reoder_item"),
    REODER_HANDLE("reorder_handle", "reorder_handle"),
    Selector_HANDLE("selector_handle", "selector_handle"),
    SELEECT_ITEM("select_item", "select_item");

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
