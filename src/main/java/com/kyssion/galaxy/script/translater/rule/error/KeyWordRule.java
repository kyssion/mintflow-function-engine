package com.kyssion.galaxy.script.translater.rule.error;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.base.Rule;

public class KeyWordRule extends Rule {

    private String keyWord;

    public KeyWordRule(String keyWord){
        this.keyWord = keyWord;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return this.keyWord.equals(data.getValue());
    }
}
