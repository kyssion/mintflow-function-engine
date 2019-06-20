package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.error.ZRule;

import java.util.List;

/**
 * a = namespaceId
 * b = processid
 * c = handleid
 * Z = SZ|#
 * S = namespace(a){K}
 * K = process(b)P;K|#
 * P = ->h:{c}P|#
 * 语法分析树
 */
public class GrammaAnalysis {

    public static ZRule analysis(List<LexicalAnalysisData> dataList) {
        ZRule zRule = new ZRule();
        zRule.tryChild(0, dataList);
        return zRule;
    }
}
