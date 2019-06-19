package com.kyssion.galaxy.script.translater.symbol;

public enum SymbolType {
    namespace("namespace", 0),
    process("process", 1),
    namespaceId("namespaceId", 2),
    processId("processId", 3),
    left_bracket("(", 4),
    right_bracket(")", 5),
    left_l_bracket("{", 6),
    right_l_bracket("}", 7),
    handlerId("handleId", 8),
    handler("h", 9),
    underline("-", 10),
    right_z_bracket(">", 11),
    semicolon(";", 12);

    SymbolType(String name, int code) {
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
