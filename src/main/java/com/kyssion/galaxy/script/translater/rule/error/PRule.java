package com.kyssion.galaxy.script.translater.rule.error;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.base.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * P = ->h:{C}P|#
 */
public class PRule extends Rule {

    @Override
    public void init() {
        this.rulesList = new ArrayList<>();
        List<Rule> list = new ArrayList<>();
        list.add(new UnderlineRule());
        list.add(new ArrowRule());
        list.add(new ColonRule());
        list.add(new LeftLBracketRule());
        list.add(new CRule());
        list.add(new RightLBracketRule());
        list.add(new PRule());
        rulesList.add(list);
        List<Rule> emple = new ArrayList<>();
        emple.add(new EmpleRule());
        rulesList.add(emple);
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
