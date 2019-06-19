package com.kyssion.galaxy.script.translater.data.workKeyData;

import com.kyssion.galaxy.script.translater.symbol.LexicalType;

public class LexicalAnalysisData {
    private String value;
    private int index;
    private LexicalType type;

    public LexicalAnalysisData() {
        super();
    }

    public LexicalAnalysisData(String value, int index, LexicalType type) {
        this.value = value;
        this.index = index;
        this.type = type;
    }

    public static LexicalAnalysisData create(String value, int index, LexicalType type) {
        return new LexicalAnalysisData(value, index, type);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
