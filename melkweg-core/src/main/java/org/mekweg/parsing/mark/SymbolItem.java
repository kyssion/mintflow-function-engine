package org.mekweg.parsing.mark;

public class SymbolItem extends Word {
    public SymbolItem(String value) {
        this.setName("symbol_item");
        this.setType(WordType.SAMEPLE_WORD);
        this.setValue(value);
    }
    public SymbolItem(String value,int line,int wordNum) {
        this(value);
        setLineNum(line);
        setWordStartNum(wordNum);
    }
}
