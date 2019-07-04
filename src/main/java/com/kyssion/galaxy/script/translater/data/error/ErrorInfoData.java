package com.kyssion.galaxy.script.translater.data.error;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.languageErrorType.LanguageErrorType;

public class ErrorInfoData {
    private int lineId;
    private int wordId;
    private String value;
    private LanguageErrorType type;

    public ErrorInfoData(int lineId, int wordId, String value, LanguageErrorType type) {
        this.lineId = lineId;
        this.wordId = wordId;
        this.value = value;
        this.type = type;
    }

    public ErrorInfoData(LexicalAnalysisData data,LanguageErrorType type){
        this(data.getLineIndex(),data.getIndex(),data.getValue(),type);
    }

    public ErrorInfoData(){
        super();
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LanguageErrorType getType() {
        return type;
    }

    public void setType(LanguageErrorType type) {
        this.type = type;
    }
}
