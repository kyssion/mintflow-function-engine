package com.kyssion.galaxy.script.translater.symbol;

/**
 * 关键字类型
 */
public enum LexicalType {
    namespace("namespace", 0),
    process("process", 1),
    left_bracket("(", 2),
    right_bracket(")", 3),
    left_l_bracket("{", 4),
    right_l_bracket("}", 5),
    handler("h", 6),
    underline("-", 7),
    right_z_bracket(">", 8),
    semicolon(";", 9),
    Id("", 10);

    LexicalType(String name, int code) {
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

    public static LexicalType getTypeByKeyWord(String keyWord) {
        for (LexicalType type : values()) {
            if(type.getName().equals(keyWord)){
                return type;
            }
        }
        return LexicalType.Id;
    }
}
