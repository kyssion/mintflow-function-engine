package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.base.Rule;
import com.kyssion.galaxy.script.translater.rule.typeCheck.IdTypeRule;

import java.util.List;

/**
 * :
 */
public class ColonRule extends Rule {
    @Override
    public int tryChild(int index , List<LexicalAnalysisData> dataList) {
        if (dataList.get(index).getValue().equals(":")) {
            return index+1;
        }
        return -1;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return data.getValue().equals(":");
    }
}