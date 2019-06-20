package com.kyssion.galaxy.script.translater.rule.error;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.base.Rule;

import java.util.List;

/**
 * >
 */
public class ArrowRule extends Rule {
    
    @Override
    public int tryChild(int index, List<LexicalAnalysisData> dataList) {
        if (dataList.get(index).getValue().equals(">")) {
            return index + 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return data.getValue().equals(">");
    }
}
