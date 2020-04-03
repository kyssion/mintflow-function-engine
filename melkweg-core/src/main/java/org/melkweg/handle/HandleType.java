package org.melkweg.handle;

/**
 * Used to describe the type that the current handler belongs to
 */
public enum  HandleType {

    UNDERFIND_HANDLE("underfind handle",0),SAMPLE_HANDLE("sample handle",1),CONDITION_HANDLE("condition handle",2),
    REORDER_HANDLE("reorder handle",3),CONDITION_HANDLE_WRAPPER("condition handle wrapper",4);

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
