package org.mintflow.handler;

/**
 * Used to describe the type that the current handler belongs to
 */
public enum  HandlerType {

    UNDERFIND_HANDLE_SYNC("underfind handle sync",0),
    SAMPLE_HANDLE_SYNC("sample handle sync",1),
    CONDITION_HANDLE_SYNC("condition handle sync",2),
    REORDER_HANDLE_SYNC("reorder handle sync",3),
    CONDITION_HANDLE_WRAPPER_SYNC("condition handle wrapper sync",4),
    CYCLE_HANDLE_SYNC("cycle handle sync",5),
    CYCLE_HANDLE_WRAPPER_SYNC("cycle handle wrapper sync",6),

    UNDERFIND_HANDLE_ASYNC("underfind handle async",7),
    SAMPLE_HANDLE_ASYNC("sample handle async",8),
    CONDITION_HANDLE_ASYNC("condition handle async",9),
    REORDER_HANDLE_ASYNC("reorder handle async",10),
    CONDITION_HANDLE_WRAPPER_ASYNC("condition handle wrapper async",11),
    CYCLE_HANDLE_ASYNC("cycle handle async",12),
    CYCLE_HANDLE_WRAPPER_ASYNC("cycle handle wrapper async",13),
    ASYNC_HANDLE("ASYNC_HANDLE",14),
    SYNC_HANDLE("SYNC_HANDLE",15);

    HandlerType(String name,int code){
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
