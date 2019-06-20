package com.kyssion.galaxy.script.translater.rule.error;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.base.Rule;

import java.util.List;

public class EmpleRule extends Rule {

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return true;
    }

    @Override
    public int tryChild(int index, List<LexicalAnalysisData> dataList) {
        return index + 1;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
