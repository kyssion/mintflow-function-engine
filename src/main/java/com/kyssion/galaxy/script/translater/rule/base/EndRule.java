package com.kyssion.galaxy.script.translater.rule.base;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

public abstract class EndRule extends Rule {
    public abstract boolean isMatch(LexicalAnalysisData data);
}
