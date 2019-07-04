package com.kyssion.galaxy.script.translater.analysis;

import com.kyssion.galaxy.exception.AnalysisNoHandleException;
import com.kyssion.galaxy.handle.Handle;
import com.kyssion.galaxy.handle.StartHandler;
import com.kyssion.galaxy.handle.reoder.ReorderActuatorHandle;
import com.kyssion.galaxy.handle.reoder.ReorderHandle;
import com.kyssion.galaxy.handle.select.ElseSelectHandle;
import com.kyssion.galaxy.handle.select.SelectorHandle;
import com.kyssion.galaxy.handle.select.SelectorStartHandle;
import com.kyssion.galaxy.handle.type.Type;
import com.kyssion.galaxy.param.ParamWrapper;
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

    public SemanticAnalysis() {
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

    private Map<String, StartHandler> startHandleMap;
    private Deque<List<Handle>> handleListStack;
    private LexicalAnalysisData namespaceData;
    private LexicalAnalysisData processData;
    private String startMapKey;
    private Map<String, Handle> handleMap;

    public int analysis(List<LexicalAnalysisData> dataList, Map<String, Handle> map) {
        this.tryItemStack = new LinkedList<>();
        this.index = 0;
        handleMap = map;
        this.startHandleMap = new HashMap<>();
        handleListStack = new LinkedList<>();
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
                            this.namespaceData = dataList.get(index);
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
                if(!handleListStack.isEmpty()){
                    handleListStack.removeLast();
                }
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
        StartHandler startHander;
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
                    //初始化构建list
                    startHander = new StartHandler();
                    this.processData = dataList.get(index);
                    this.startMapKey = this.namespaceData.getValue() + "." + this.processData.getValue();
                    this.startHandleMap.put(startMapKey, startHander);
                    handleListStack.addLast(startHander.getHandleList());
                    index++;
                    break;
                case "P":
                    analysis(dataList, GrammaType.P);
                    if(!handleListStack.isEmpty()){
                        handleListStack.removeLast();
                    }
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

    //->elif(d){P}E|#
    private void eAnalysis(List<LexicalAnalysisData> dataList, SelectorStartHandle selectorStartHandle) {
        int itemIndex = index;
        SelectorHandle selectorHandle = null;
        Handle handleItem;
        label:
        for (int a = 0; a < elKey.length && index < dataList.size(); a++) {
            switch (elKey[a]) {
                case "P":
                    analysis(dataList, GrammaType.P);
                    if(!handleListStack.isEmpty()){
                        handleListStack.removeLast();
                    }
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "d":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    handleItem = this.handleMap.get(dataList.get(index).getValue());
                    if (handleItem == null || handleItem.getType() != Type.SELEECT_ITEM) {
                        throw new AnalysisNoHandleException("no handle name {" + dataList.get(index).getValue() +
                                "} in process {" + this.namespaceData.getValue() + "}" + "which namespace is {"
                                + this.processData.getValue() + "}");
                    }
                    selectorHandle = (SelectorHandle) handleItem;
                    selectorStartHandle.getOtherSelector().add(selectorHandle);
                    List<Handle> list = new ArrayList<>();
                    selectorStartHandle.getSelectorItemList().add(list);
                    handleListStack.addLast(list);
                    index++;
                    break;
                case "E":
                    eAnalysis(dataList, selectorStartHandle);
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
        List<Handle> handles = null;
        List<Handle> itemhandleList = null;
        Handle handleItem = null;
        SelectorHandle selectorHandle = null;
        SelectorStartHandle selectorStartHandle = null;
        ReorderActuatorHandle reorderActuatorHandle=null;
        ReorderHandle reorderHandle = null;
        List<Handle> list;
        label:
        for (int a = 0; a < key.length && index < dataList.size(); a++) {
            switch (key[a]) {
                case "x":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    handleItem = this.handleMap.get(dataList.get(index).getValue());
                    if (handleItem == null) {
                        throw new AnalysisNoHandleException("no handle name {" + dataList.get(index).getValue() +
                                "} in process {" + this.namespaceData.getValue() + "}" + "which namespace is {"
                                + this.processData.getValue() + "}");
                    }
                    handles = handleListStack.getLast();
                    handles.add(handleItem);
                    index++;
                    break;
                case "y":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    handleItem = this.handleMap.get(dataList.get(index).getValue());
                    if (handleItem == null || handleItem.getType() != Type.REODER_ITEM) {
                        throw new AnalysisNoHandleException("no handle name {" + dataList.get(index).getValue() +
                                "} in process {" + this.namespaceData.getValue() + "}" + "which namespace is {"
                                + this.processData.getValue() + "}");
                    }
                    reorderActuatorHandle = new ReorderActuatorHandle();
                    reorderHandle = (ReorderHandle) handleItem;
                    reorderActuatorHandle.setReorderHandle(reorderHandle);
                    handles = handleListStack.getLast();
                    handles.add(reorderActuatorHandle);
                    list = new ArrayList<>();
                    reorderActuatorHandle.setListHandle(list);
                    handleListStack.addLast(list);
                    index++;
                    break;
                case "z":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    selectorStartHandle = new SelectorStartHandle();
                    handleItem = this.handleMap.get(dataList.get(index).getValue());
                    if (handleItem == null || handleItem.getType() != Type.SELEECT_ITEM) {
                        throw new AnalysisNoHandleException("no handle name {" + dataList.get(index).getValue() +
                                "} in process {" + this.namespaceData.getValue() + "}" + "which namespace is {"
                                + this.processData.getValue() + "}");
                    }
                    selectorHandle = (SelectorHandle) handleItem;
                    handles = handleListStack.getLast();
                    handles.add(selectorStartHandle);
                    selectorStartHandle.getOtherSelector().add(selectorHandle);
                    list = new ArrayList<>();
                    selectorStartHandle.getSelectorItemList().add(list);
                    handleListStack.addLast(list);
                    index++;
                    break;
                case "w":
                    if (!IdTypeRule.isTrue(dataList.get(index).getValue())) {
                        index = -1;
                        break label;
                    }
                    String[] rHandleArr = dataList.get(index).getValue().split(",");
                    handles = handleListStack.getLast();
                    for (String handleId : rHandleArr) {
                        handleItem = this.handleMap.get(handleId);
                        if (handleItem == null || handleItem.getType() != Type.HANDLE) {
                            throw new AnalysisNoHandleException("no handle name {" + dataList.get(index).getValue() +
                                    "} in process {" + this.namespaceData.getValue() + "}" + "which namespace is {"
                                    + this.processData.getValue() + "}");
                        }
                        handles.add(handleItem);
                    }
                    if(!handleListStack.isEmpty()){
                        handleListStack.removeLast();
                    }
                    index++;
                    break;
                case "P":
                    analysis(dataList, GrammaType.P);
                    if (index == -1) {
                        break label;
                    }
                    if (keyIndex == 3) {
                        if(!handleListStack.isEmpty()){
                            handleListStack.removeLast();
                        }
                    }
                    break;
                case "E":
                    eAnalysis(dataList, selectorStartHandle);
                    if (index == -1) {
                        break label;
                    }
                    break;
                case "el":
                    if (!dataList.get(index).getValue().equals(key[a])) {
                        analysis(dataList, GrammaType.P);
                        return;
                    }
                    if (selectorStartHandle == null) {
                        index = -1;
                        break label;
                    }
                    selectorStartHandle.getOtherSelector().add(new ElseSelectHandle());
                    list = new ArrayList<>();
                    selectorStartHandle.getSelectorItemList().add(list);
                    handleListStack.addLast(list);
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

    public Map<String, StartHandler> getStartHandleMap() {
        return this.startHandleMap;
    }

}
