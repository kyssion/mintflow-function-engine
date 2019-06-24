package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.typeCheck.IdTypeRule;
import com.kyssion.galaxy.script.translater.symbol.GrammaType;

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

    private String[] pKey;
    private String[] kKey;
    private String[] sKey;

    public GrammaAnalysis() {
        pKey = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        kKey = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        sKey = new String[]{
                "namespace", "(", "a", ")", "P", "{", "K", "}"
        };
    }

    public boolean analysis(List<LexicalAnalysisData> dataList) {
        return analysis(dataList, GrammaType.ROOT, 0);
    }

    private boolean analysis(List<LexicalAnalysisData> dataList, GrammaType nodeType, int index) {
        if (index >= dataList.size()) {
            return true;
        }
        switch (nodeType) {
            case ROOT:
                return analysis(dataList, GrammaType.Z, 0);
            case a:
            case b:
            case c:
                return IdTypeRule.isTrue(dataList.get(index).getValue());
            case Z:
                return analysis(dataList, GrammaType.S, index) &&
                        analysis(dataList, GrammaType.Z, index + 1) ||
                        analysis(dataList, GrammaType.EMPLE, index);

            case S:

            case K:

            case P:

        }
    }

    private int analysisS(List<LexicalAnalysisData> dataList, int index) {
        if (index >= dataList.size()) {
            return dataList.size();
        }

        for (int a = 0; a < sKey.length && index < dataList.size(); a++, index++) {
            if (sKey[a].equals("P")) {
                index = analysisP(dataList,index);
                if(index==-1){
                    break;
                }
            } else if (sKey[a].equals("K")) {

            } else if (sKey[a].equals("namespace")) {

            } else {

            }
        }
    }

    private int analysisK(List<LexicalAnalysisData> dataList, int index) {
        String[] key = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        for (int a = 0; a < kKey.length && index < dataList.size(); a++, index++) {
            if (kKey[a].equals("b")) {
                if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                    index = -1;
                    break;
                }
            } else if (kKey[a].equals("P")) {
                index = analysisP(dataList, index + 1);
                if (index == -1) {
                    break;
                }
            } else if (kKey[a].equals("K")) {
                index = analysisK(dataList, index + 1);
                if (index == -1) {
                    break;
                }
            } else {
                if (!kKey[a].equals(dataList.get(index).getValue())) {
                    index = -1;
                    break;
                }
            }
        }
        if (index == -1 &&) {

        }
        return index;
    }

    private int analysisS(List<LexicalAnalysisData> dataList, int index) {
        String[] key = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        for (int a = 0; a < key.length && index < dataList.size(); a++, index++) {
            if (key[a].equals("b")) {
                if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                    return -1;
                }
            } else if (key[a].equals("P")) {
                analysis(dataList, GrammaType.P, index + 1);
            }
        }
    }

    private int analysisP(List<LexicalAnalysisData> dataList, int index) {
        String[] key = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        for (int a = 0; a < key.length && index < dataList.size(); a++, index++) {
            if (key[a].equals("b")) {
                if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                    return -1;
                }
            } else if (key[a].equals("P")) {
                analysis(dataList, GrammaType.P, index + 1);
            }
        }
    }
}
