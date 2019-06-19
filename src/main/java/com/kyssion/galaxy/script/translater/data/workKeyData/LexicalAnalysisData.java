package com.kyssion.galaxy.script.translater.data.workKeyData;

import com.kyssion.galaxy.script.translater.symbol.LexicalType;

public class LexicalAnalysisData {
    private String code;
    private int index;
    private LexicalType type;

    public LexicalAnalysisData() {
        super();
    }

    public LexicalAnalysisData(String code, int index, LexicalType type) {
        this.code = code;
        this.index = index;
        this.type = type;
    }

    public static LexicalAnalysisData create(String code, int index, LexicalType type) {
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

    public LexicalType getType() {
        return type;
    }

    public void setType(LexicalType type) {
        this.type = type;
    }
}
