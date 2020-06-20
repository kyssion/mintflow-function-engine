package org.melkweg.handle;

/**
 * Used to describe the type that the current handler belongs to
 */
public enum  HandleType {

    UNDERFIND_HANDLE_SYNC("underfind handle",0),
    SAMPLE_HANDLE_SYNC("sample handle",1),
    CONDITION_HANDLE_SYNC("condition handle",2),
    REORDER_HANDLE__SYNC("reorder handle",3),
    CONDITION_HANDLE_WRAPPER_SYNC("condition handle wrapper",4),
    CYCLE_HANDLE_SYNC("cycle handle",5),
    CYCLE_HANDLE_WRAPPER_SYNC("cycle handle wrapper",6);

    HandleType(String name,int code){
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
