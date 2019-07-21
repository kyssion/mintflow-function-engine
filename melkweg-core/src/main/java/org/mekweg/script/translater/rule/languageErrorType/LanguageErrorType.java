package org.mekweg.script.translater.rule.languageErrorType;

public enum LanguageErrorType {

    NAME_SPACE("name_space", "在此处尝试文法S - namespace(:namespaceid){K文法}"),
    PROCESS("process", "在此处尝试文法K - process(:processId)P文法;K文法或者空"),
    HANDLE("handle", "在此处尝试文法P法 - ->h(C)P文法"),
    REORDERING("reordering", "在此处尝试文法P - ->r(c){','号分割的handleId列表}P文法"),
    IF("if", "在此处尝试文法P - ->if(:handleId){P文法}E文法+el{P文法}"),
    ELIF("elif", "在此处尝试文法E - ->elif(c){P文法}E文法或空");
    private String way;
    private String desc;

    LanguageErrorType(String way, String desc) {
        this.way = way;
        this.desc = desc;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
