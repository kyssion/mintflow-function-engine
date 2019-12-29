package org.mekweg.parsing.mark;

import java.util.logging.Handler;

public enum WordType {
    DELIMITER_KEY_WORD("delimiter",0),TERMINATOR("End symbol",1),
    STATEMENT_KEY_WORD("statement Key word",2),SAMEPLE_WORD("basic type",3),
    HANDLE_KEY_WORD("handle key word",4),OTHER_KEY_WORD("Other key word",5);
    private WordType(String name,int code){
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

    public static boolean isStatementKeyWord(WordType keyWordType){
        return keyWordType==STATEMENT_KEY_WORD;
    }

    public static boolean isHandleKeyWord(WordType keyWordType){
        return keyWordType== HANDLE_KEY_WORD;
    }

    public static boolean isTerminator(WordType keyWordType){
        return keyWordType == TERMINATOR;
    }

    public static boolean isDelimiter(WordType wordType){
        return wordType==DELIMITER_KEY_WORD;
    }
}
