package com.kyssion.galaxy.script.type;

public enum HandleTypeEnum {

    StartHandle("start_handle",1),
    Handle("process_handle",2);
    private String typeDesc;
    private int typeCode;

    HandleTypeEnum(String typeDesc, int typeCode) {
        this.typeDesc = typeDesc;
        this.typeCode = typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
