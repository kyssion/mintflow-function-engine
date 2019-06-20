package com.kyssion.galaxy.script.translater.rule.error.base;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;

import java.util.List;

public abstract class Rule {

//    public abstract void init();

    protected List<List<Rule>> rulesList;

    public int tryChild(int index, List<LexicalAnalysisData> dataList) {
        if(index>=dataList.size()){
            return -1;
        }
        for (List<Rule> list : rulesList) {
            if (list.get(0).isMatch(dataList.get(index))) {
                for (Rule rule : list) {
                    if (rule.isEnd()) {
                        if (rule.tryChild(index, dataList) == -1) {
                            return -1;
                        }
                        index++;
                    } else {
                        index = rule.tryChild(index, dataList);
                    }
                }
                return index;
            }
        }
        return -1;
    }
    public abstract boolean isEnd();
    public abstract boolean isMatch(LexicalAnalysisData data);

}
