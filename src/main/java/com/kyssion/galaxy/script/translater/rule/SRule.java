package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.base.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * S = namespace(A){K}
 */
public class SRule extends Rule {


    public SRule(){
        this.rulesList = new ArrayList<>();
        List<Rule> list = new ArrayList<>();
        list.add(new ARule());
        list.add(new LeftBracketRule());
        list.add(new KeyWordRule("namespace"));
        list.add(new RightBracketRule());
        list.add(new LeftLBracketRule());
        list.add(new KRule());
        list.add(new RightLBracketRule());
        rulesList.add(list);
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return true;
    }
}
