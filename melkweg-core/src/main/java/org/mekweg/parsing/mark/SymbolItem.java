package org.mekweg.parsing.mark;

public class SymbolItem extends Word {
    public SymbolItem(String value) {
        this.setType(WordType.VALUE);
        this.setValue(value);
    }
    public SymbolItem(String value,int line,int wordNum) {
        this(value);
        setLineNum(line);
        setWordStartNum(wordNum);
    }
}
