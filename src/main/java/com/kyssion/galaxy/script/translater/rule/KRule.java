package com.kyssion.galaxy.script.translater.rule;

import com.kyssion.galaxy.script.translater.rule.base.MiddleRule;
import com.kyssion.galaxy.script.translater.rule.base.Rule;

import java.util.ArrayList;
import java.util.List;

// process(B)P;K|#
public class KRule extends MiddleRule {

    private List<Rule> list;

    public KRule() {
        list = new ArrayList<>();
        list.add(new ProcessRule());
        list.add(new LeftBracketRule());
    }

    @Override
    public void tryChild() {

    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
