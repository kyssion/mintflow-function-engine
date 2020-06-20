package org.melkweg.parsing;

import org.melkweg.exception.ParsingRuntimeException;
import org.melkweg.handle.*;
import org.melkweg.handle.FnHandler;
import org.melkweg.handle.async.AsyncConditionFncHandlerWrapper;
import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;
import org.melkweg.param.ParamWrapper;
import org.melkweg.parsing.mark.Word;
import org.melkweg.parsing.mark.WordType;
import org.melkweg.process.ProcessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FnEngineDataStructureTool {

    private Map<String, FnHandler> syncHandlerDataMap;
    private Map<String,FnHandler> asyncHandleDataMap;

    public FnEngineDataStructureTool(Map<HandleType,Map<String, FnHandler>> handlerDataMap) {
        if (handlerDataMap == null) {
            handlerDataMap = new HashMap<>();
        }
        this.syncHandlerDataMap = handlerDataMap.get(HandleType.SYNC_HANDLE);
        this.asyncHandleDataMap = handlerDataMap.get(HandleType.ASYNC_HANDLE);
    }

    public Map<String, Map<ProcessType,Map<String, List<FnHandler>>>> runGrammarAnalysisTool(List<Word> list) {
        Map<String, Map<ProcessType,Map<String, List<FnHandler>>>> dataStructureMap = new HashMap<>();
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            Word namespaceWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingRuntimeException("当前未发现命名空间标记(namespace)", namespaceWord);
            }
            Map<ProcessType,Map<String, List<FnHandler>>> processMap = new HashMap<>();
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

    private Map<ProcessType,Map<String, List<FnHandler>>> processHandleListBuild(List<Word> list, int start, int end) {
        Map<ProcessType,Map<String, List<FnHandler>>> processMap = new HashMap<>();
        while (start <= end) {
            boolean isAsync = false;
            Word processword = list.get(start);
            switch (processword.getType()){
                case SYNC:
                    processword = list.get(++start);
                    if (processword.getType() != WordType.PROCESS) {
                        throw new ParsingRuntimeException("当前位置错误,没有发现process标记'", processword);
                    }
                    break;
                case ASYNC:
                    isAsync = true;
                    processword = list.get(++start);
                    if (processword.getType() != WordType.PROCESS) {
                        throw new ParsingRuntimeException("当前位置错误,没有发现process标记'", processword);
                    }
                    break;
                default:
                    throw new ParsingRuntimeException("当前位置错误,没有发现process标记和 sync 或者 async标记'", processword);

            }
            String processName = findName(list, ++start);
            List<FnHandler> fnHandlerList = new ArrayList<>();
            if(!isAsync){
                Map<String ,List<FnHandler>> listMap = processMap.computeIfAbsent(ProcessType.SYNC,k->new HashMap<>());
                listMap.put(processName, fnHandlerList);
            }else{
                Map<String ,List<FnHandler>> listMap = processMap.computeIfAbsent(ProcessType.ASYNC,k->new HashMap<>());
                listMap.put(processName, fnHandlerList);
            }
            //+3 是因为 词法在这个位置是 （name） 会被分成3个词
            // processBlockEnd 为当前process 会映射到的最后一个位置（包括这个位置）
            int processBlockEnd = findBlockEndIndex(list, start + 3, end);
            //+4 是因为 词法在这个位置是 （processName）{ 会被分成4个词
            fnHandlerList.addAll(handleListBuild(list, start + 4, processBlockEnd - 1,isAsync));
            start = processBlockEnd + 1;
        }
        return processMap;
    }

    private List<FnHandler> handleListBuild(List<Word> list, int start, int end,boolean isAsync) {
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
                    fnHandler = getFnHandle(handleName,isAsync);
                    if (fnHandler != null) {
                        if(fnHandler.getType()==HandleType.SAMPLE_HANDLE_ASYNC||fnHandler.getType()==HandleType.SAMPLE_HANDLE_SYNC){
                            try {
                                fnHandlerList.add(fnHandler.clone());
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle初始化失败 , handle 名称 :"+handleName + "类型: "+(isAsync?"异步":"同步"), handlerWord);
                            }
                        }else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为samplehandler"+ "  类型: "+(isAsync?"异步":"同步"), handlerWord);
                        }
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "  类型: "+(isAsync?"异步":"同步"), handlerWord);
                    }
                    start = start + 4;
                    break;
                case REORDER_HANDLE:
                    handleName = findName(list, start + 1);
                    fnHandler = getFnHandle(handleName,isAsync);
                    if (fnHandler != null) {
                        if(fnHandler.getType()==HandleType.REORDER_HANDLE_SYNC||fnHandler.getType()==HandleType.REORDER_HANDLE_ASYNC) {
                            ToolsFnHandle reorderHandler = null;
                            try {
                                reorderHandler = ((ToolsFnHandle) fnHandler).clone();
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :" + handleName + "类型: "+(isAsync?"异步":"同步"), handlerWord);
                            }
                            int reorderEnd = findBlockEndIndex(list, start + 4, end);
                            List<FnHandler> childs = handleListBuild(list, start + 5, reorderEnd - 1, isAsync);
                            reorderHandler.addChilds(childs);
                            fnHandlerList.add(reorderHandler);
                            start = reorderEnd + 1;
                        }
                        else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为reorder类型handler" + "类型: "+(isAsync?"异步":"同步"), handlerWord);
                        }
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "类型: "+(isAsync?"异步":"同步"), handlerWord);
                    }
                case CYCLE_HANDLE:
                    break;
                case CONDITION_IF_HANDLE:
                    ToolsConditonHandlerWrapper toolsConditonHandlerWrapper = isAsync?new AsyncConditionFncHandlerWrapper():new SyncConditionFncHandlerWrapper();
                    fnHandlerList.add(toolsConditonHandlerWrapper);
                    start = conditionHandleListBuildSync(list, start, end, toolsConditonHandlerWrapper,isAsync);
                    break;
                default:
                    throw new ParsingRuntimeException("当前语法发生错误,此处应该为流程标记符号", handlerWord);
            }
        }
        return fnHandlerList;
    }

    private int conditionHandleListBuildSync(List<Word> list, int start, int end, ToolsConditonHandlerWrapper wrapper,boolean isAysnc) {
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
                    fnHandler = syncHandlerDataMap.get(handlerName);
                    if (fnHandler != null) {
                        ToolsFnHandle conditionHander;
                        try {
                            conditionHander = (ToolsFnHandle) fnHandler.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handlerName, wordKey);
                        }
                        int endIndex = findBlockEndIndex(list, start + 4, end);
                        List<FnHandler> childers = handleListBuild(list, start + 5, endIndex - 1,isAysnc);
                        conditionHander.addChilds(childers);
                        wrapper.addConditionHandle(conditionHander);
                        start = endIndex + 1;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handlerName, wordKey);
                    }
                    break;
                case CONDITION_ELSE_HANDLE:
                    if (!findIf) {
                        throw new ParsingRuntimeException("当前流程判断语法错误 , if-else if-elif-else 语法块格式不正确", wordKey);
                    }
                    ToolsFnHandle conditionHander = createDefaultElseCondition(isAysnc);
                    int endIndex = findBlockEndIndex(list, start + 1, end);
                    List<FnHandler> childers = handleListBuild(list, start + 2, endIndex - 1,isAysnc);
                    conditionHander.addChilds(childers);
                    wrapper.addConditionHandle(conditionHander);
                    start = endIndex + 1;
                    break;
                default:
                    return start;
            }
        }
        return start;
    }

    private ToolsFnHandle createDefaultElseCondition(boolean isAsync){
        return isAsync?new AsyncConditionFncHandlerWrapper.ConditionHandler("CONDITION:ELSE") {
            @Override
            public boolean condition(ParamWrapper params) {
                return true;
            }
        }: new SyncConditionFncHandlerWrapper.ConditionHandler("CONDITION:ELSE") {
            @Override
            public boolean condition(ParamWrapper params) {
                return true;
            }
        };
    }

    private FnHandler getFnHandle(String handleName,boolean isAsync){
        return isAsync?asyncHandleDataMap.get(handleName):syncHandlerDataMap.get(handleName);
    }
}
