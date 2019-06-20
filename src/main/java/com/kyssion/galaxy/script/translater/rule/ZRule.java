package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.base.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Z = SZ|#
 */
public class ZRule extends Rule {

    public ZRule() {
        rulesList = new ArrayList<>();
        List<Rule> list = new ArrayList<>();
        list.add(new SRule());
        list.add(new ZRule());
        List<Rule> empleList = new ArrayList<>();
        empleList.add(new EmpleRule());
        rulesList.add(list);
        rulesList.add(empleList);
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isMatch(LexicalAnalysisData data) {
        return true;
    }
}
