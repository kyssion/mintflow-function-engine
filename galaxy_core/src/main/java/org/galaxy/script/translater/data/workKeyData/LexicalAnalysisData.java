package org.galaxy.script.translater.data.workKeyData;

import org.galaxy.script.translater.symbol.LexicalType;

public class LexicalAnalysisData {
    private String value;
    private int index;
    private LexicalType type;
    private int lineIndex;
    private String fileName;

    public LexicalAnalysisData() {
        super();
    }

    public LexicalAnalysisData(String value, LexicalType type,int lineIndex,String fileName) {
        this.value = value;
        this.type = type;
        this.lineIndex = lineIndex;
        this.fileName = fileName;
    }

    public static LexicalAnalysisData create(String value, LexicalType type,int lineIndex,String fileName) {
        return new LexicalAnalysisData(value, type,lineIndex,fileName);
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
