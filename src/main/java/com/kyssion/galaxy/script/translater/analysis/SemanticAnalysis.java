package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.StartHandler;
import com.kyssion.galaxy.script.translater.data.error.ErrorInfoData;
import com.kyssion.galaxy.script.translater.data.workKeyData.LexicalAnalysisData;
import com.kyssion.galaxy.script.translater.rule.languageErrorType.LanguageErrorType;
import com.kyssion.galaxy.script.translater.rule.typeCheck.IdTypeRule;
import com.kyssion.galaxy.script.translater.symbol.GrammaType;

import java.util.*;

/**
 * createStartHandler
 */
public class SemanticAnalysis {

    private Deque<ErrorInfoData> tryItemStack;
    private int index;
    private String[] pKey1;
    private String[] pKey2;
    private String[] pKey3;
    private String[] kKey;
    private String[] sKey;
    private String[] elKey;

    private Map<String, Handle> handleMap;
    private Map<String, StartHandler> startHanderMap;
    private LexicalAnalysisData namespaceData;
    private LexicalAnalysisData processData;
    private String startMapKey;

    public SemanticAnalysis() {
        pKey1 = new String[]{
                "-", ">", "h", "(", "c", ")", "P"
        };
        pKey2 = new String[]{
                "-", ">", "r", "(", "c", ")", "{", "d", "}", "P"
        };
        pKey3 = new String[]{
                "-", ">", "if", "(", "c", ")", "{", "P", "}", "E", "el", "{", "P", "}", "P"
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

    public int analysis(List<LexicalAnalysisData> dataList, Map<String, Handle> map) {
        this.tryItemStack = new LinkedList<>();
        this.index = 0;
        this.handleMap = map;
        this.startHanderMap = new HashMap<>();
        analysis(dataList, GrammaType.ROOT);
        return index;
    }

    private List<Handle> analysis(List<LexicalAnalysisData> dataList, GrammaType nodeType) {
        if (index >= dataList.size()) {
            index = dataList.size();
            return new ArrayList<>();
        }
        int itemIndex;
        switch (nodeType) {
            case ROOT:
                analysis(dataList, GrammaType.Z);
                return new ArrayList<>();
            case a:
            case b:
            case c:
                index = IdTypeRule.isTrue(dataList.get(index).getValue()) ? index + 1 : -1;
                return new ArrayList<>();
            case Z: //Z = SZ|#
                analysis(dataList, GrammaType.S);
                itemIndex = index;
                if (itemIndex != -1) {
                    tryItemStack = new LinkedList<>();
                    analysis(dataList, GrammaType.Z);
                }
                if (index == -1) {
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return new ArrayList<>();
                }
                return new ArrayList<>();
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
                                return new ArrayList<>();
                            }
                            index++;
                            break;
                        default:
                            if (!dataList.get(index).getValue().equals(sKey[a])) {
                                this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), LanguageErrorType.NAME_SPACE));
                                index = -1;
                                return new ArrayList<>();
                            }
                            index++;
                            break;
                    }
                }
                return new ArrayList<>();
            case K: // K = process(b)P;K|#
                itemIndex = index;
                index = kAnalysis(dataList);
                if (index == -1) {
                    index = itemIndex;
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return new ArrayList<>();
                }
                return new ArrayList<>();
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
                    return new ArrayList<>();
                }
                return new ArrayList<>();

            case E://->elif(c){P}E|#
                itemIndex = index;
                eAnalysis(dataList);
                if (index == -1) {
                    index = itemIndex;
                    analysis(dataList, GrammaType.HAS_ERROR_EMPLE);
                    return new ArrayList<>();
                }
                return new ArrayList<>();
            case EMPLE:
                tryItemStack.removeLast();
                return new ArrayList<>();
            case HAS_ERROR_EMPLE:
                return new ArrayList<>();
        }
        index = -1;
        return new ArrayList<>();
    }

    // K = process(b)P;K|#
    private int kAnalysis(List<LexicalAnalysisData> dataList) {
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
                    //初始化植入process
                    this.processData = dataList.get(index);
                    StartHandler startHander = new StartHandler();
                    this.startMapKey = this.namespaceData.getValue() + "." + this.processData.getValue();
                    this.startHanderMap.put(startMapKey, startHander);
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
        return index;
    }

    //->elif(c){P}E|#
    private List<Handle> eAnalysis(List<LexicalAnalysisData> dataList) {
        label:
        for (int a = 0; a < elKey.length && index < dataList.size(); a++) {
            switch (elKey[a]) {
                case "P":
                    analysis(dataList, GrammaType.P);
                    if (index == -1) {
                        return new ArrayList<>();
                    }
                    break;
                case "d":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        return new ArrayList<>();
                    }
                    index++;
                    break;
                case "E":
                    analysis(dataList, GrammaType.E);
                    if (index == -1) {
                        return new ArrayList<>();
                    }
                    break;
                default:
                    if (!dataList.get(index).getValue().equals(elKey[a])) {
                        this.tryItemStack.addLast(new ErrorInfoData(dataList.get(index), LanguageErrorType.ELIF));
                        index = -1;
                        return new ArrayList<>();
                    }
                    index++;
                    break;
            }
        }
        return new ArrayList<>();
    }

    //->h(C)P|->if(xxx){P}E el(){}|->r(c){handleIdList}P|#
    private List<Handle> pAnalysis(List<LexicalAnalysisData> dataList, String[] key, int keyIndex) {
        label:
        for (int a = 0; a < key.length && index < dataList.size(); a++) {
            switch (key[a]) {
                case "c":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        return new ArrayList<>();
                    }
                    index++;
                    break;
                case "d":
                    index++;
                    break;
                case "P":
                    analysis(dataList, GrammaType.P);
                    if (index == -1) {
                        return new ArrayList<>();
                    }
                    break;
                case "E":
                    analysis(dataList, GrammaType.E);
                    if (index == -1) {
                        return new ArrayList<>();
                    }
                    break;
                case "el":
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        analysis(dataList, GrammaType.P);
                        return new ArrayList<>();
                    }
                    index++;
                    break;
                case "h":
                case "r":
                case "if":
                default:
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        if (keyIndex == 3 && key[a].equals("el")) {
                            return new ArrayList<>();
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
        return new ArrayList<>();
    }

    public Deque<ErrorInfoData> getTryItemDuque() {
        return tryItemStack;
    }
}
