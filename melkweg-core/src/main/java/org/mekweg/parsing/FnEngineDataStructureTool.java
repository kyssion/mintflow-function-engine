package org.mekweg.parsing;

import org.mekweg.exception.ParsingRuntimeError;
import org.mekweg.handle.ConditionHandlerWrapper;
import org.mekweg.handle.HandleType;
import org.mekweg.handle.Handler;
import org.mekweg.handle.ReorderHandler;
import org.mekweg.handle.SampleHandler;
import org.mekweg.param.ParamWrapper;
import org.mekweg.parsing.mark.Word;
import org.mekweg.parsing.mark.WordType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnEngineDataStructureTool {

    private Map<String, Handler> handlerDataMap;

    public FnEngineDataStructureTool(Map<String, Handler> handlerDataMap) {
        if (handlerDataMap == null) {
            handlerDataMap = new HashMap<>();
        }
        this.handlerDataMap = handlerDataMap;
    }

    public Map<String, Map<String, List<Handler>>> runGrammarAnalysisTool(List<Word> list) {
        Map<String, Map<String, List<Handler>>> dataStructureMap = new HashMap<>();
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            Word namespaceWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingRuntimeError("当前未发现命名空间标记(namespace)", namespaceWord);
            }
            Map<String, List<Handler>> processMap = new HashMap<>();
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
            throw new ParsingRuntimeError("当前未发命名起始标记'('", nameStartKey);
        }
        Word name = list.get(start++);
        if (name.getType() != WordType.VALUE) {
            throw new ParsingRuntimeError("当前值命名错误,不可为关键字", name);
        }
        Word nameEndKey = list.get(start);
        if (nameEndKey.getType() != WordType.NAME_SPLIT_END_KEY) {
            throw new ParsingRuntimeError("当前未发命名终止标记')'", nameEndKey);
        }
        return name.getValue();
    }

    private int findBlockEndIndex(List<Word> list, int start, int end) {
        Word blockStartWord = list.get(start);
        if (blockStartWord.getType() != WordType.BLOCKE_START_KEY) {
            throw new ParsingRuntimeError("当前未发现块起始字符'{'", blockStartWord);
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
            throw new ParsingRuntimeError("当前namespace块没有闭合,未发现对应的'}'字符", blockStartWord);
        }
        return start;
    }

    private Map<String, List<Handler>> processHandleListBuild(List<Word> list, int start, int end) {
        Map<String, List<Handler>> processMap = new HashMap<>();
        while (start <= end) {
            Word processword = list.get(start);
            if (processword.getType() != WordType.PROCESS) {
                throw new ParsingRuntimeError("当前位置错误,没有发现process标记'", processword);
            }
            String processName = findName(list, start+1);
            List<Handler> handlerList = new ArrayList<>();
            processMap.put(processName, handlerList);
            int processBlockEnd = findBlockEndIndex(list, start + 4, end);
            handlerList.addAll(handleListBuild(list, start + 5, processBlockEnd - 1));
            start = processBlockEnd + 1;
        }
        return processMap;
    }

    private List<Handler> handleListBuild(List<Word> list, int start, int end) {
        List<Handler> handlerList = new ArrayList<>();
        while (start <= end) {
            Word handleLinkKeyWord = list.get(start);
            if (handleLinkKeyWord.getType() != WordType.HANDLE_LINK_KEY) {
                throw new ParsingRuntimeError("当前语法发生错误,非法流程前缀标记", handleLinkKeyWord);
            }
            start++;
            Word handlerWord = list.get(start);
            String handleName;
            Handler handler;
            switch (handlerWord.getType()) {
                case HANDLE:
                    handleName = findName(list, start + 1);
                    handler = handlerDataMap.get(handleName);
                    if (handler != null) {
                        if (handler.getType() != HandleType.SAMPLE_HANDLE) {
                            throw new ParsingRuntimeError("当前handle类型不正确,此处应为基本类型handler", handlerWord);
                        }
                        SampleHandler sampleHandler = (SampleHandler) handler;
                        try {
                            handlerList.add(sampleHandler.clone());
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeError("当前handle 初始化失败 , handle 名称 :"+handleName, handlerWord);
                        }
                    } else {
                        throw new ParsingRuntimeError("当前handler未找到,handler 名称" + handleName, handlerWord);
                    }
                    start = start + 4;
                    break;
                case REORDER_HANDLE:
                    handleName = findName(list, start + 1);
                    handler = handlerDataMap.get(handleName);
                    if (handler != null) {
                        if (handler.getType() != HandleType.REORDER_HANDLE) {
                            throw new ParsingRuntimeError("当前handle类型不正确,此处应为reorder类型handler", handlerWord);
                        }
                        ReorderHandler reorderHandler = null;
                        try {
                            reorderHandler = ((ReorderHandler) handler).clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeError("当前handle 初始化失败 , handle 名称 :"+handleName, handlerWord);
                        }
                        int reorderEnd = findBlockEndIndex(list, start+4, end);
                        List<Handler> childs = handleListBuild(list, start + 5, reorderEnd - 1);
                        reorderHandler.addChilds(childs);
                        handlerList.add(reorderHandler);
                        start = reorderEnd + 1;
                        break;
                    } else {
                        throw new ParsingRuntimeError("当前handler未找到,handler 名称" + handleName, handlerWord);
                    }
                case CONDITION_IF_HANDLE:
                    ConditionHandlerWrapper conditionHandlerWrapper = new ConditionHandlerWrapper();
                    handlerList.add(conditionHandlerWrapper);
                    start = conditionHandleListBuild(list, start, end, conditionHandlerWrapper);
                    break;
                default:
                    throw new ParsingRuntimeError("当前语法发生错误,此处应该为流程标记符号", handlerWord);
            }
        }
        return handlerList;
    }

    private int conditionHandleListBuild(List<Word> list, int start, int end, ConditionHandlerWrapper wrapper) {
        boolean findIf = false;
        String handlerName;
        Handler handler;
        while (start <= end) {
            Word wordKey = list.get(start);
            switch (wordKey.getType()) {
                case CONDITION_IF_HANDLE:
                case CONDITION_ELSE_IF_HANDLE:
                    if (wordKey.getType() == WordType.CONDITION_IF_HANDLE) {
                        findIf = true;
                    }
                    if (!findIf) {
                        throw new ParsingRuntimeError("当前流程判断语法错误 , if-else if-else 语法块格式不正确", wordKey);
                    }
                    handlerName = findName(list, start + 1);
                    handler = handlerDataMap.get(handlerName);
                    if (handler != null) {
                        ConditionHandlerWrapper.ConditionHander conditionHander =
                                null;
                        try {
                            conditionHander = ((ConditionHandlerWrapper.ConditionHander) handler).clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeError("当前handle 初始化失败 , handle 名称 :"+handlerName, wordKey);
                        }
                        int endIndex = findBlockEndIndex(list, start + 4, end);
                        List<Handler> childers = handleListBuild(list, start + 5, endIndex - 1);
                        conditionHander.addChilds(childers);
                        wrapper.addChilds(conditionHander);
                        start = endIndex + 1;
                    } else {
                        throw new ParsingRuntimeError("当前handler未找到,handler 名称" + handlerName, wordKey);
                    }
                    break;
                case CONDITION_ELSE_HANDLE:
                    if (!findIf) {
                        throw new ParsingRuntimeError("当前流程判断语法错误 , if-else if-else 语法块格式不正确", wordKey);
                    }
                    ConditionHandlerWrapper.ConditionHander conditionHander =
                            new ConditionHandlerWrapper.ConditionHander() {
                                @Override
                                public boolean condition(ParamWrapper params) {
                                    return true;
                                }
                            };
                    int endIndex = findBlockEndIndex(list, start + 1, end);
                    List<Handler> childers = handleListBuild(list, start + 2, endIndex - 1);
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
