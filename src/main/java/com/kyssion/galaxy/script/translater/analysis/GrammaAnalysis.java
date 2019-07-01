package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.typeCheck.IdTypeRule;
import com.kyssion.galaxy.script.translater.symbol.GrammaType;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    private Deque<LexicalAnalysisData> errorItemStack;

    private String[] pKey1;
    private String[] pKey2;
    private String[] pKey3;
    private String[] kKey;
    private String[] sKey;
    private String[] elKey;

    public GrammaAnalysis() {
        pKey1 = new String[]{
                "-", ">", "h", "(", "c", ")", "P"
        };
        pKey2 = new String[]{
                "-", ">", "r", "(", "c", ")", "{", "d", "}", "P"
        };
        pKey3 = new String[]{
                "-", ">", "if", "(", "c", ")", "{", "P", "}", "E", "el", "{", "P", "}"
        };
        elKey = new String[]{
                "elif", "(", "d", ")", "{", "P", "}", "E"
        };
        kKey = new String[]{
                "process", "(", "b", ")", "P", ";", "K"
        };
        sKey = new String[]{
                "namespace", "(", "a", ")", "{", "K", "}"
        };
    }

    public int analysis(List<LexicalAnalysisData> dataList) {
        this.errorItemStack = new LinkedList<>();
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
                    return analysis(dataList,GrammaType.HAS_ERROR_EMPLE,index);
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
                                this.errorItemStack.addLast(dataList.get(index));
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
                index = kAnalysis(dataList, index);
                if (index == -1) {
                    return analysis(dataList, GrammaType.HAS_ERROR_EMPLE, itemIndex);
                }
                return index;
            case P: //P = ->h(c)P|#
                itemIndex = index;
                index = pAnalysis(dataList, index, pKey1, 1);
                if (index == -1) {
                    errorItemStack.removeLast();
                    index = pAnalysis(dataList, itemIndex, pKey2, 2);
                }
                if (index == -1) {
                    errorItemStack.removeLast();
                    index = pAnalysis(dataList, itemIndex, pKey3, 3);
                }
                if (index == -1) {
                    return analysis(dataList, GrammaType.HAS_ERROR_EMPLE, itemIndex);
                }
                return index;

            case E://->elif(c){P}E|#
                itemIndex = index;
                index = eAnalysis(dataList, index);
                if (index == -1) {
                    return analysis(dataList, GrammaType.HAS_ERROR_EMPLE, itemIndex);
                }
                return index;
            case EMPLE:
                errorItemStack.removeLast();
                return index;
            case HAS_ERROR_EMPLE:
                return index;
        }
        return -1;
    }

    // K = process(b)P;K|#
    private int kAnalysis(List<LexicalAnalysisData> dataList, int index) {
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
                        this.errorItemStack.addLast(dataList.get(index));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
        return index;
    }

    //->elif(c){P}E|#
    private int eAnalysis(List<LexicalAnalysisData> dataList, int index) {
        label:
        for (int a = 0; a < elKey.length && index < dataList.size(); a++) {
            switch (elKey[a]) {
                case "P":
                    index = analysis(dataList, GrammaType.P, index);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "d":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
                case "E":
                    index = analysis(dataList, GrammaType.E, index);
                    if (index == -1) {
                        break label;
                    }
                    break;
                default:
                    if (!dataList.get(index).getValue().equals(elKey[a])) {
                        this.errorItemStack.addLast(dataList.get(index));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
        return index;
    }

    //->h(C)P|->if(xxx){P}E el(){}|->r(c){handleIdList}P|#
    private int pAnalysis(List<LexicalAnalysisData> dataList, int index, String[] key, int keyIndex) {
        label:
        for (int a = 0; a < key.length && index < dataList.size(); a++) {
            switch (key[a]) {
                case "c":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
                case "d":
                    index++;
                    break;
                case "P":
                    index = analysis(dataList, GrammaType.P, index);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "E":
                    index = analysis(dataList, GrammaType.E, index);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "h":
                case "r":
                case "if":
                default:
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        if (keyIndex == 3 && key[a].equals("el")) {
                            return index;
                        }
                        this.errorItemStack.addLast(dataList.get(index));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
        return index;
    }

    public Deque<LexicalAnalysisData> getErrorItemDuque() {
        return errorItemStack;
    }
}
