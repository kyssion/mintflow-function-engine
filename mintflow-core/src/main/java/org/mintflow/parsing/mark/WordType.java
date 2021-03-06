package org.mintflow.parsing.mark;

public enum WordType {

    NAMESPACE("namespace"),
    PROCESS("process"),
    HANDLE("handle"),
    REORDER_HANDLE("reorder handle"),
    CYCLE_HANDLE("cycle handle"),
    CONDITION_IF_HANDLE("if handle"),
    CONDITION_ELSE_IF_HANDLE("else if handle"),
    CONDITION_ELSE_HANDLE("else handle"),
    NAME_SPLIT_START_KEY("("),NAME_SPLIT_END_KEY(")"),
    BLOCKE_START_KEY("{"),
    BLOCKE_END_KEY("}"),
    HANDLE_LINK_KEY("->"),
    VALUE("value"),
    ASYNC("async"),
    SPLICE_I(":"),
    SYNC("sync"),
    VAR("var"),
    EXPORT("export");

    WordType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
