package org.mekweg.parsing.mark;

public enum WordType {

    STATEMENT_KEY_WORD("statement Key word", 1), SAMEPLE_WORD("basic type", 2),
    HANDLE_KEY_WORD("handle key word", 3), OTHER_KEY_WORD("Other key word", 4),
    NAME_SPLIT_START_KEY("name split start key", 5), NAME_SPLIT_END_KEY("name splite end key", 6),
    BLOCKE_START_KEY("block start key", 7), BLOCKE_END_KEY("blocke end key", 8),HANDLE_LINK_KEY("handle link key",9);

    WordType(String name, int code) {
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
