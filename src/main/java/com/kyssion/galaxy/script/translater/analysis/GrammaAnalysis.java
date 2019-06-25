package com.kyssion.galaxy.script.translater.analysis;

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

    private LexicalAnalysisData errorItem;

    private String[] pKey;
    private String[] kKey;
    private String[] sKey;

    public GrammaAnalysis() {
        pKey = new String[]{
                "-", ">", "h", ":", "{", "c", "}", "P"
        };
        kKey = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        sKey = new String[]{
                "namespace", "(", "a", ")", "{", "K", "}"
        };
    }

    public int analysis(List<LexicalAnalysisData> dataList) {
        this.errorItem = null;
        return analysis(dataList, GrammaType.ROOT, 0);
    }

    private int analysis(List<LexicalAnalysisData> dataList, GrammaType nodeType, int index) {
        if (index >= dataList.size()) {
            return dataList.size();
        }
        int itemIndex;
        switch (nodeType) {
            case ROOT:
                return analysis(dataList, GrammaType.Z, 0);
            case a:
            case b:
            case c:
                return IdTypeRule.isTrue(dataList.get(index).getValue()) ? index + 1 : -1;
            case Z: //Z = SZ|#
                itemIndex = analysis(dataList, GrammaType.S, index);
                if (itemIndex != -1) {
                    itemIndex = analysis(dataList, GrammaType.Z, itemIndex);
                }
                if (itemIndex == -1) {
                    return analysis(dataList, GrammaType.EMPLE, index);
                }
                return itemIndex;
            case S: //S = namespace(a){K}
                label:
                for (int a = 0; a < sKey.length && index < dataList.size(); a++) {
                    switch (sKey[a]) {
                        case "K":
                            index = analysis(dataList, GrammaType.K, index);
                            if (index < 0) {
                                break label;
                            }
                            break;
                        case "a":
                            if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                        default:
                            if (!dataList.get(index).getValue().equals(sKey[a])) {
                                this.errorItem=dataList.get(index);
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                    }
                }
                return index;
            case K: // K = process(b)P;K|#
                itemIndex = index;
                label:
                for (int a = 0; a < kKey.length && index < dataList.size(); a++) {
                    switch (kKey[a]) {
                        case "K":
                            index = analysis(dataList, GrammaType.K, index);
                            if (index == -1) {
                                break label;
                            }
                            break;
                        case "b":
                            if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                        case "P":
                            index = analysis(dataList, GrammaType.P, index);
                            if (index == -1) {
                                break label;
                            }
                            break;
                        default:
                            if (!dataList.get(index).getValue().equals(kKey[a])) {
                                this.errorItem=dataList.get(index);
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                    }
                }
                if (index == -1) {
                    return analysis(dataList, GrammaType.EMPLE, itemIndex);
                }
                return index;
            case P: //P = ->h:{c}P|#
                itemIndex = index;
                label:
                for (int a = 0; a < pKey.length && index < dataList.size(); a++) {
                    switch (pKey[a]) {
                        case "c":
                            if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                        case "P":
                            index = analysis(dataList, GrammaType.P, index);
                            if (index == -1) {
                                break label;
                            }
                            break;
                        default:
                            if (!dataList.get(index).getValue().equals(pKey[a])) {
                                this.errorItem=dataList.get(index);
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                    }
                }
                if (index == -1) {
                    return analysis(dataList, GrammaType.EMPLE, itemIndex);
                }
                return index;
            case EMPLE:
                return index;
        }
        return -1;
    }

    public LexicalAnalysisData getErrorItem() {
        return errorItem;
    }

    public String[] getpKey() {
        return pKey;
    }

    public void setpKey(String[] pKey) {
        this.pKey = pKey;
    }

    public String[] getkKey() {
        return kKey;
    }

    public void setkKey(String[] kKey) {
        this.kKey = kKey;
    }

    public String[] getsKey() {
        return sKey;
    }

    public void setsKey(String[] sKey) {
        this.sKey = sKey;
    }
}
