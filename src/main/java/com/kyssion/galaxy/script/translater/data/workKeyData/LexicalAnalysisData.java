package com.kyssion.galaxy.script.translater.data.workKeyData;

import com.kyssion.galaxy.script.translater.symbol.SymbolType;

public class LexicalAnalysisData {
    private String code;
    private int index;
    private SymbolType type;

    public LexicalAnalysisData() {
        super();
    }

    public LexicalAnalysisData(String code, int index, SymbolType type) {
        this.code = code;
        this.index = index;
        this.type = type;
    }

    public static LexicalAnalysisData create(String code, int index, SymbolType type) {
        return new LexicalAnalysisData(code, index, type);
    }

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
