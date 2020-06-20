package org.melkweg.parsing;

import org.melkweg.exception.ParsingRuntimeException;
import org.melkweg.handle.*;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.sync.SyncReorderFnHandler;
import org.melkweg.handle.sync.SyncSampleFnHandler;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.parsing.mark.Word;
import org.melkweg.parsing.mark.WordType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnEngineDataStructureTool {

    private Map<String, FnHandler> handlerDataMap;

    public FnEngineDataStructureTool(Map<String, FnHandler> handlerDataMap) {
        if (handlerDataMap == null) {
            handlerDataMap = new HashMap<>();
        }
        this.handlerDataMap = handlerDataMap;
    }

    public Map<String, Map<String, List<FnHandler>>> runGrammarAnalysisTool(List<Word> list) {
        Map<String, Map<String, List<FnHandler>>> dataStructureMap = new HashMap<>();
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            Word namespaceWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingRuntimeException("当前未发现命名空间标记(namespace)", namespaceWord);
            }
            Map<String, List<FnHandler>> processMap = new HashMap<>();
            String name = findName(list, start + 1);
            dataStructureMap.put(name, processMap);
            int namespaceEnd = findBlockEndIndex(list, start + 4, end);
            processMap.putAll(processHandleListBuild(list, start + 5, namespaceEnd - 1));
            start = namespaceEnd + 1;
        }
        return dataStructureMap;
    }

    private String findName(List<Word> list, int start) {
        Word nameStartKey = list.get(start++);
        if (nameStartKey.getType() != WordType.NAME_SPLIT_START_KEY) {
            throw new ParsingRuntimeException("当前未发命名起始标记'('", nameStartKey);
        }
        Word name = list.get(start++);
        if (name.getType() != WordType.VALUE) {
            throw new ParsingRuntimeException("当前值命名错误,不可为关键字", name);
        }
        Word nameEndKey = list.get(start);
        if (nameEndKey.getType() != WordType.NAME_SPLIT_END_KEY) {
            throw new ParsingRuntimeException("当前未发命名终止标记')'", nameEndKey);
        }
        return name.getValue();
    }

    private int findBlockEndIndex(List<Word> list, int start, int end) {
        Word blockStartWord = list.get(start);
        if (blockStartWord.getType() != WordType.BLOCKE_START_KEY) {
            throw new ParsingRuntimeException("当前未发现块起始字符'{'", blockStartWord);
        }
        int blockStartNum = 1;
        start++;
        while (start <= end) {
            if (list.get(start).getType() == WordType.BLOCKE_START_KEY) {
                blockStartNum++;
            } else if (list.get(start).getType() == WordType.BLOCKE_END_KEY) {
                blockStartNum--;
            }
            if(blockStartNum==0){
                break;
            }
            start++;
        }
        if (blockStartNum != 0) {
            throw new ParsingRuntimeException("当前namespace块没有闭合,未发现对应的'}'字符", blockStartWord);
        }
        return start;
    }

    private Map<String, List<FnHandler>> processHandleListBuild(List<Word> list, int start, int end) {
        Map<String, List<FnHandler>> processMap = new HashMap<>();
        while (start <= end) {
            Word processword = list.get(start);
            if (processword.getType() != WordType.PROCESS) {
                throw new ParsingRuntimeException("当前位置错误,没有发现process标记'", processword);
            }
            String processName = findName(list, start+1);
            List<FnHandler> fnHandlerList = new ArrayList<>();
            processMap.put(processName, fnHandlerList);
            int processBlockEnd = findBlockEndIndex(list, start + 4, end);
            fnHandlerList.addAll(handleListBuild(list, start + 5, processBlockEnd - 1));
            start = processBlockEnd + 1;
        }
        return processMap;
    }

    private List<FnHandler> handleListBuild(List<Word> list, int start, int end) {
        List<FnHandler> fnHandlerList = new ArrayList<>();
        while (start <= end) {
            Word handleLinkKeyWord = list.get(start);
            if (handleLinkKeyWord.getType() != WordType.HANDLE_LINK_KEY) {
                throw new ParsingRuntimeException("当前语法发生错误,非法流程前缀标记", handleLinkKeyWord);
            }
            start++;
            Word handlerWord = list.get(start);
            String handleName;
            FnHandler fnHandler;
            switch (handlerWord.getType()) {
                case HANDLE:
                    handleName = findName(list, start + 1);
                    fnHandler = handlerDataMap.get(handleName);
                    if (fnHandler != null) {
                        if (fnHandler.getType() != HandleType.SAMPLE_HANDLE_SYNC) {
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为基本类型handler", handlerWord);
                        }
                        SyncSampleFnHandler sampleHandler = (SyncSampleFnHandler) fnHandler;
                        try {
                            fnHandlerList.add(sampleHandler.clone());
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handleName, handlerWord);
                        }
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName, handlerWord);
                    }
                    start = start + 4;
                    break;
                case REORDER_HANDLE:
                    handleName = findName(list, start + 1);
                    fnHandler = handlerDataMap.get(handleName);
                    if (fnHandler != null) {
                        if (fnHandler.getType() != HandleType.REORDER_HANDLE_SYNC) {
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为reorder类型handler", handlerWord);
                        }
                        SyncReorderFnHandler reorderHandler = null;
                        try {
                            reorderHandler = ((SyncReorderFnHandler) fnHandler).clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handleName, handlerWord);
                        }
                        int reorderEnd = findBlockEndIndex(list, start+4, end);
                        List<FnHandler> childs = handleListBuild(list, start + 5, reorderEnd - 1);
                        reorderHandler.addChilds(childs);
                        fnHandlerList.add(reorderHandler);
                        start = reorderEnd + 1;
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName, handlerWord);
                    }
                case CYCLE_HANDLE:
                    break;
                case CONDITION_IF_HANDLE:
                    SyncConditionFncHandlerWrapper conditionHandlerWrapper = new SyncConditionFncHandlerWrapper();
                    fnHandlerList.add(conditionHandlerWrapper);
                    start = conditionHandleListBuild(list, start, end, conditionHandlerWrapper);
                    break;
                default:
                    throw new ParsingRuntimeException("当前语法发生错误,此处应该为流程标记符号", handlerWord);
            }
        }
        return fnHandlerList;
    }

    private int conditionHandleListBuild(List<Word> list, int start, int end, SyncConditionFncHandlerWrapper wrapper) {
        boolean findIf = false;
        String handlerName;
        FnHandler fnHandler;
        while (start <= end) {
            Word wordKey = list.get(start);
            switch (wordKey.getType()) {
                case CONDITION_IF_HANDLE:
                case CONDITION_ELSE_IF_HANDLE:
                    if (wordKey.getType() == WordType.CONDITION_IF_HANDLE) {
                        findIf = true;
                    }
                    if (!findIf) {
                        throw new ParsingRuntimeException("当前流程判断语法错误 , if-else if-else 语法块格式不正确", wordKey);
                    }
                    handlerName = findName(list, start + 1);
                    fnHandler = handlerDataMap.get(handlerName);
                    if (fnHandler != null) {
                        SyncConditionFncHandlerWrapper.ConditionHander conditionHander;
                        try {
                            conditionHander = ((SyncConditionFncHandlerWrapper.ConditionHander) fnHandler).clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handlerName, wordKey);
                        }
                        int endIndex = findBlockEndIndex(list, start + 4, end);
                        List<FnHandler> childers = handleListBuild(list, start + 5, endIndex - 1);
                        conditionHander.addChilds(childers);
                        wrapper.addChilds(conditionHander);
                        start = endIndex + 1;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handlerName, wordKey);
                    }
                    break;
                case CONDITION_ELSE_HANDLE:
                    if (!findIf) {
                        throw new ParsingRuntimeException("当前流程判断语法错误 , if-else if-elif-else 语法块格式不正确", wordKey);
                    }
                    SyncConditionFncHandlerWrapper.ConditionHander conditionHander =
                            new SyncConditionFncHandlerWrapper.ConditionHander("CONDITION:ELSE") {
                                @Override
                                public boolean condition(ParamWrapper params) {
                                    return true;
                                }
                            };
                    int endIndex = findBlockEndIndex(list, start + 1, end);
                    List<FnHandler> childers = handleListBuild(list, start + 2, endIndex - 1);
                    conditionHander.addChilds(childers);
                    wrapper.addChilds(conditionHander);
                    start = endIndex + 1;
                    break;
                default:
                    return start;
            }
        }
        return start;
    }
}
