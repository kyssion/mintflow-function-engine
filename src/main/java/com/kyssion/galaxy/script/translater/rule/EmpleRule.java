package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.base.EndRule;

public class EmpleRule extends EndRule {

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return true;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
