package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.script.translater.data.error.ErrorInfoData;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.languageErrorType.LanguageErrorType;
import com.kyssion.galaxy.script.translater.rule.typeCheck.IdTypeRule;
import com.kyssion.galaxy.script.translater.symbol.GrammaType;

import java.util.Deque;
import java.util.LinkedList;
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

    private Deque<ErrorInfoData> tryItemStack;
    private int index;
    private String[] pKey1;
    private String[] pKey2;
    private String[] pKey3;
    private String[] kKey;
    private String[] sKey;
    private String[] elKey;

    public GrammaAnalysis() {
        pKey1 = new String[]{
                "-", ">", "h", "(", "x", ")", "P"
        };
        pKey2 = new String[]{
                "-", ">", "r", "(", "y", ")", "{", "w", "}", "P"
        };
        pKey3 = new String[]{
                "-", ">", "if", "(", "z", ")", "{", "P", "}", "E", "el", "{", "P", "}", "P"
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
        this.tryItemStack = new LinkedList<>();
        this.index = 0;
        analysis(dataList, GrammaType.ROOT);
        return index;
    }

    private void analysis(List<LexicalAnalysisData> dataList, GrammaType nodeType) {
        if (index >= dataList.size()) {
            index = dataList.size();
            return;
        }
        int itemIndex;
        switch (nodeType) {
            case ROOT:
                analysis(dataList, GrammaType.Z);
                return;
            case Z: //Z = SZ|#
                analysis(dataList, GrammaType.S);
                itemIndex = index;
                if (itemIndex != -1) {
                    tryItemStack = new LinkedList<>();
                    analysis(dataList, GrammaType.Z);
                }
                if (index == -1) {
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return;
                }
                return;
            case S: //S = namespace(a){K}
                label:
                for (int a = 0; a < sKey.length && index < dataList.size(); a++) {
                    switch (sKey[a]) {
                        case "K":
                            analysis(dataList, GrammaType.K);
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
                                this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), LanguageErrorType.NAME_SPACE));
                                index = -1;
                                break label;
                            }
                            index++;
                            break;
                    }
                }
                return;
            case K: // K = process(b)P;K|#
                itemIndex = index;
                kAnalysis(dataList);
                if (index == -1) {
                    index = itemIndex;
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return;
                }
                return;
            case P: //P = ->h(c)P|#
                itemIndex = index;
                pAnalysis(dataList, pKey1, 1);
                if (index == -1) {
                    tryItemStack.removeLast();
                    index = itemIndex;
                    pAnalysis(dataList, pKey2, 2);
                }
                if (index == -1) {
                    tryItemStack.removeLast();
                    index = itemIndex;
                    pAnalysis(dataList, pKey3, 3);
                }
                if (index == -1) {
                    index = itemIndex;
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return;
                }
                return;
//            case E://->elif(c){P}E|#
//                itemIndex = index;
//                eAnalysis(dataList);
//                if (index == -1) {
//                    index = itemIndex;
//                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
//                    return;
//                }
//                return;
            case EMPLE:
                tryItemStack.removeLast();
                return;
            case HAS_ERROR_EMPLE:
                return;
        }
        index = -1;
    }

    // K = process(b)P;K|#
    private void kAnalysis(List<LexicalAnalysisData> dataList) {
        label:
        for (int a = 0; a < kKey.length && index < dataList.size(); a++) {
            switch (kKey[a]) {
                case "K":
                    tryItemStack = new LinkedList<>();
                    analysis(dataList, GrammaType.K);
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
                    analysis(dataList, GrammaType.P);
                    if (index == -1) {
                        break label;
                    }
                    break;
                default:
                    if (!dataList.get(index).getValue().equals(kKey[a])) {
                        this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), LanguageErrorType.PROCESS));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
    }

    //->elif(c){P}E|#
    private void eAnalysis(List<LexicalAnalysisData> dataList) {
        int itemIndex = index;
        label:
        for (int a = 0; a < elKey.length && index < dataList.size(); a++) {
            switch (elKey[a]) {
                case "P":
                    analysis(dataList, GrammaType.P);
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
                    eAnalysis(dataList);
                    if (index == -1) {
                        break label;
                    }
                    break;
                default:
                    if (!dataList.get(index).getValue().equals(elKey[a])) {
                        this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), LanguageErrorType.ELIF));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
        if (index == -1) {
            index = itemIndex;
            analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
        }
    }

    //->h(C)P|->if(xxx){P}E el(){}|->r(c){handleIdList}P|#
    private void pAnalysis(List<LexicalAnalysisData> dataList, String[] key, int keyIndex) {
        label:
        for (int a = 0; a < key.length && index < dataList.size(); a++) {
            switch (key[a]) {
                case "x":
                case "y":
                case "z":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
                case "w":
                    index++;
                    break;
                case "P":
                    analysis(dataList, GrammaType.P);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "E":
                    eAnalysis(dataList);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "el":
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        analysis(dataList, GrammaType.P);
                        return;
                    }
                    index++;
                    break;
                case "h":
                case "r":
                case "if":
                default:
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        if (keyIndex == 3 && key[a].equals("el")) {
                            return;
                        }
                        LanguageErrorType type = keyIndex == 1 ?
                                LanguageErrorType.HANDLE : keyIndex == 2 ?
                                LanguageErrorType.REORDERING : LanguageErrorType.IF;
                        this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), type));
                        index = -1;
                        break label;
                    }
                    index++;
                    break;
            }
        }
    }

    public Deque<ErrorInfoData> getTryItemDuque() {
        return tryItemStack;
    }
}
