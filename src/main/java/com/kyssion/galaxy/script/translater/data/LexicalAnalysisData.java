package com.kyssion.galaxy.script.translater.data;

import com.kyssion.galaxy.script.translater.symbol.SymbolType;

public class LexicalAnalysisData {
    private String code;
    private int index;
    private SymbolType type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }
}
