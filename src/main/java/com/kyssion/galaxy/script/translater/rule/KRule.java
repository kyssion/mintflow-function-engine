package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.base.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * K = process(B)P;K|#
 */
public class KRule extends Rule {

    private List<List<Rule>> rulesList;

    public KRule() {
        rulesList = new ArrayList<>();
        List<Rule> list = new ArrayList<>();
        list.add(new KeyWordRule("process"));
        list.add(new LeftBracketRule());
        list.add(new BRule());
        list.add(new RightBracketRule());
        list.add(new PRule());
        list.add(new ColonRule());
        list.add(new KRule());
        rulesList.add(list);
        List<Rule> empleList = new ArrayList<>();
        empleList.add(new EmpleRule());
        rulesList.add(empleList);
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
