package org.mintflow.parsing;

import org.mintflow.exception.ParsingRuntimeException;
import org.mintflow.handler.HandlerDataMap;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.async.AsyncConditionFncHandlerWrapper;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.async.AsyncToolsFnHandler;
import org.mintflow.handler.sync.SyncConditionFncHandlerWrapper;
import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.handler.sync.SyncToolsFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.parsing.mark.Word;
import org.mintflow.parsing.mark.WordType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO 增加同名流程异常校验
public class FnEngineDataStructureTool {

    private final Map<String, SyncFnHandler> syncHandlerDataMap;
    private final Map<String, AsyncFnHandler> asyncHandlerDataMap;

    public FnEngineDataStructureTool(MintFlowHandlerMap mapper) {
        if (mapper == null) {
            syncHandlerDataMap = new HashMap<>();
            asyncHandlerDataMap = new HashMap<>();
            return;
        }
        this.syncHandlerDataMap = mapper.getSyncFnHandlerMap();
        this.asyncHandlerDataMap = mapper.getAsyncFnHandlerMap();
    }

    public HandlerDataMap runGrammarAnalysisTool(List<Word> list) {
        HandlerDataMap dataStructureMap = new HandlerDataMap();
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            Word namespaceWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingRuntimeException("当前未发现命名空间标记(namespace)", namespaceWord);
            }
            HandlerDataMap.ProcessDataMap processMap = new HandlerDataMap.ProcessDataMap();
            String name = findName(list, start + 1);
            dataStructureMap.add(name, processMap);
            int namespaceEnd = findBlockEndIndex(list, start + 4, end);
            processMap.addAll(processHandlerListBuild(list, start + 5, namespaceEnd - 1));
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

    private HandlerDataMap.ProcessDataMap processHandlerListBuild(List<Word> list, int start, int end) {
        HandlerDataMap.ProcessDataMap processMap = new HandlerDataMap.ProcessDataMap();
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
            int processBlockEnd = findBlockEndIndex(list, start + 3, end);
            //+3 是因为 词法在这个位置是 （name） 会被分成3个词
            // processBlockEnd 为当前process 会映射到的最后一个位置（包括这个位置）
            //+4 是因为 词法在这个位置是 （processName）{ 会被分成4个词
            if(isAsync){
                Map<String ,List<AsyncFnHandler>> listMap = processMap.getAsyncFnDataMap();
                List<AsyncFnHandler> asyncFnHandlerList = new ArrayList<>();
                listMap.put(processName, asyncFnHandlerList);
                asyncFnHandlerList.addAll(handleListBuildAsync(list,start+4,processBlockEnd-1));
            }else{
                Map<String ,List<SyncFnHandler>> listMap = processMap.getSyncFnDataMap();
                List<SyncFnHandler> syncFnHandlerList = new ArrayList<>();
                listMap.put(processName, syncFnHandlerList);
                syncFnHandlerList.addAll(handleListBuildSync(list, start + 4, processBlockEnd - 1));
            }
            start = processBlockEnd + 1;
        }
        return processMap;
    }

    private List<AsyncFnHandler> handleListBuildAsync(List<Word> list, int start, int end) {
        List<AsyncFnHandler> fnHandlerList = new ArrayList<>();
        while (start <= end) {
            Word handleLinkKeyWord = list.get(start);
            if (handleLinkKeyWord.getType() != WordType.HANDLE_LINK_KEY) {
                throw new ParsingRuntimeException("当前语法发生错误,非法流程前缀标记", handleLinkKeyWord);
            }
            start++;
            Word handlerWord = list.get(start);
            String handleName;
            AsyncFnHandler asyncFnHandler;
            switch (handlerWord.getType()) {
                case HANDLE:
                    handleName = findName(list, start + 1);
                    asyncFnHandler = asyncHandlerDataMap.get(handleName);
                    if (asyncFnHandler != null) {
                        if(asyncFnHandler.getType()== HandlerType.SAMPLE_HANDLE_ASYNC){
                            try {
                                fnHandlerList.add(asyncFnHandler.clone());
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle初始化失败 , handle 名称 :"+handleName + "类型: 异步", handlerWord);
                            }
                        }else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为samplehandler"+ "  类型: " + "类型: 异步", handlerWord);
                        }
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "  类型: " + "类型: 异步", handlerWord);
                    }
                    start = start + 4;
                    break;
                case REORDER_HANDLE:
                    handleName = findName(list, start + 1);
                    asyncFnHandler = asyncHandlerDataMap.get(handleName);
                    if (asyncFnHandler != null) {
                        if(asyncFnHandler.getType()==HandlerType.REORDER_HANDLE_ASYNC) {
                            AsyncToolsFnHandler reorderHandler = null;
                            try {
                                reorderHandler = ((AsyncToolsFnHandler) asyncFnHandler).clone();
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :" + handleName + "类型: 异步", handlerWord);
                            }
                            int reorderEnd = findBlockEndIndex(list, start + 4, end);
                            List<AsyncFnHandler> childs = handleListBuildAsync(list, start + 5, reorderEnd - 1);
                            reorderHandler.addChildren(childs);
                            fnHandlerList.add(reorderHandler);
                            start = reorderEnd + 1;
                        }
                        else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为AsyncReorder子类型handler" + "类型: 异步", handlerWord);
                        }
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "类型: 异步", handlerWord);
                    }
                case CYCLE_HANDLE:
                    handleName = findName(list, start + 1);
                    asyncFnHandler = asyncHandlerDataMap.get(handleName);
                    if (asyncFnHandler != null) {
                        if(asyncFnHandler.getType()==HandlerType.CYCLE_HANDLE_ASYNC) {
                            AsyncToolsFnHandler reorderHandler = null;
                            try {
                                reorderHandler = ((AsyncToolsFnHandler) asyncFnHandler).clone();
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :" + handleName + "类型: 异步", handlerWord);
                            }
                            int reorderEnd = findBlockEndIndex(list, start + 4, end);
                            List<AsyncFnHandler> childs = handleListBuildAsync(list, start + 5, reorderEnd - 1);
                            reorderHandler.addChildren(childs);
                            fnHandlerList.add(reorderHandler);
                            start = reorderEnd + 1;
                        }
                        else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为AsycnCycle类型handler" + "类型: 异步", handlerWord);
                        }
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "类型: 异步", handlerWord);
                    }
                case CONDITION_IF_HANDLE:
                    AsyncConditionFncHandlerWrapper toolsConditonHandlerWrapper = new AsyncConditionFncHandlerWrapper();
                    fnHandlerList.add(toolsConditonHandlerWrapper);
                    start = conditionHandlerListBuildAsync(list, start, end, toolsConditonHandlerWrapper);
                    break;
                default:
                    throw new ParsingRuntimeException("当前语法发生错误,此处应该为流程标记符号", handlerWord);
            }
        }
        return fnHandlerList;
    }

    private List<SyncFnHandler> handleListBuildSync(List<Word> list, int start, int end) {
        List<SyncFnHandler> fnHandlerList = new ArrayList<>();
        while (start <= end) {
            Word handleLinkKeyWord = list.get(start);
            if (handleLinkKeyWord.getType() != WordType.HANDLE_LINK_KEY) {
                throw new ParsingRuntimeException("当前语法发生错误,非法流程前缀标记", handleLinkKeyWord);
            }
            start++;
            Word handlerWord = list.get(start);
            String handleName;
            SyncFnHandler syncFnHandler;
            switch (handlerWord.getType()) {
                case HANDLE:
                    handleName = findName(list, start + 1);
                    syncFnHandler = syncHandlerDataMap.get(handleName);
                    if (syncFnHandler != null) {
                        if(syncFnHandler.getType()==HandlerType.SAMPLE_HANDLE_SYNC){
                            try {
                                fnHandlerList.add(syncFnHandler.clone());
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle初始化失败 , handle 名称 :"+handleName + "类型: 同步", handlerWord);
                            }
                        }else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为samplehandler"+ "  类型: "+ "类型: 同步", handlerWord);
                        }
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "  类型: "+ "类型: 同步", handlerWord);
                    }
                    start = start + 4;
                    break;
                case REORDER_HANDLE:
                    handleName = findName(list, start + 1);
                    syncFnHandler = syncHandlerDataMap.get(handleName);
                    if (syncFnHandler != null) {
                        if(syncFnHandler.getType()==HandlerType.REORDER_HANDLE_SYNC) {
                            SyncToolsFnHandler reorderHandler = null;
                            try {
                                reorderHandler = ((SyncToolsFnHandler) syncFnHandler).clone();
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :" + handleName + "类型: 同步", handlerWord);
                            }
                            int reorderEnd = findBlockEndIndex(list, start + 4, end);
                            List<SyncFnHandler> childs = handleListBuildSync(list, start + 5, reorderEnd - 1);
                            reorderHandler.addChildren(childs);
                            fnHandlerList.add(reorderHandler);
                            start = reorderEnd + 1;
                        }
                        else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为syncReorder类型handler" + "类型: 异步", handlerWord);
                        }
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "类型: 异步", handlerWord);
                    }
                case CYCLE_HANDLE:
                    handleName = findName(list, start + 1);
                    syncFnHandler = syncHandlerDataMap.get(handleName);
                    if (syncFnHandler != null) {
                        if(syncFnHandler.getType()==HandlerType.CYCLE_HANDLE_SYNC) {
                            SyncToolsFnHandler reorderHandler = null;
                            try {
                                reorderHandler = ((SyncToolsFnHandler) syncFnHandler).clone();
                            } catch (CloneNotSupportedException e) {
                                throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :" + handleName + "类型: 同步", handlerWord);
                            }
                            int reorderEnd = findBlockEndIndex(list, start + 4, end);
                            List<SyncFnHandler> childs = handleListBuildSync(list, start + 5, reorderEnd - 1);
                            reorderHandler.addChildren(childs);
                            fnHandlerList.add(reorderHandler);
                            start = reorderEnd + 1;
                        }
                        else{
                            throw new ParsingRuntimeException("当前handle类型不正确,此处应为syncCycle类型handler" + "类型: 异步", handlerWord);
                        }
                        break;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handleName + "类型: 异步", handlerWord);
                    }
                case CONDITION_IF_HANDLE:
                    SyncConditionFncHandlerWrapper syncConditionFncHandlerWrapper = new SyncConditionFncHandlerWrapper();
                    fnHandlerList.add(syncConditionFncHandlerWrapper);
                    start = conditionHandlerListBuildSync(list, start, end, syncConditionFncHandlerWrapper);
                    break;
                default:
                    throw new ParsingRuntimeException("当前语法发生错误,此处应该为流程标记符号", handlerWord);
            }
        }
        return fnHandlerList;
    }
    private int conditionHandlerListBuildAsync(List<Word> list, int start, int end, AsyncConditionFncHandlerWrapper wrapper) {
        boolean findIf = false;
        String handlerName;
        AsyncFnHandler asyncFnHandler;
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
                    asyncFnHandler = asyncHandlerDataMap.get(handlerName);
                    if (asyncFnHandler != null) {
                        AsyncToolsFnHandler conditionHander;
                        try {
                            conditionHander = (AsyncToolsFnHandler) asyncFnHandler.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handlerName, wordKey);
                        }
                        int endIndex = findBlockEndIndex(list, start + 4, end);
                        List<AsyncFnHandler> childers = handleListBuildAsync(list, start + 5, endIndex - 1);
                        conditionHander.addChildren(childers);
                        wrapper.addChildren(conditionHander);
                        start = endIndex + 1;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handlerName, wordKey);
                    }
                    break;
                case CONDITION_ELSE_HANDLE:
                    if (!findIf) {
                        throw new ParsingRuntimeException("当前流程判断语法错误 , if-else if-elif-else 语法块格式不正确", wordKey);
                    }
                    AsyncToolsFnHandler conditionHander = new AsyncConditionFncHandlerWrapper.ConditionHandler("CONDITION:ELSE") {
                        @Override
                        public boolean condition(ParamWrapper params) {
                            return true;
                        }
                    };
                    int endIndex = findBlockEndIndex(list, start + 1, end);
                    List<AsyncFnHandler> childers = handleListBuildAsync(list, start + 2, endIndex - 1);
                    conditionHander.addChildren(childers);
                    wrapper.addChildren(conditionHander);
                    start = endIndex + 1;
                    break;
                default:
                    return start;
            }
        }
        return start;
    }
    private int conditionHandlerListBuildSync(List<Word> list, int start, int end, SyncConditionFncHandlerWrapper wrapper) {
        boolean findIf = false;
        String handlerName;
        SyncFnHandler syncFnHandler;
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
                    syncFnHandler = syncHandlerDataMap.get(handlerName);
                    if (syncFnHandler != null) {
                        SyncToolsFnHandler conditionHander;
                        try {
                            conditionHander = (SyncToolsFnHandler) syncFnHandler.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new ParsingRuntimeException("当前handle 初始化失败 , handle 名称 :"+handlerName, wordKey);
                        }
                        int endIndex = findBlockEndIndex(list, start + 4, end);
                        List<SyncFnHandler> childers = handleListBuildSync(list, start + 5, endIndex - 1);
                        conditionHander.addChildren(childers);
                        wrapper.addChildren(conditionHander);
                        start = endIndex + 1;
                    } else {
                        throw new ParsingRuntimeException("当前handler未找到,handler 名称" + handlerName, wordKey);
                    }
                    break;
                case CONDITION_ELSE_HANDLE:
                    if (!findIf) {
                        throw new ParsingRuntimeException("当前流程判断语法错误 , if-else if-elif-else 语法块格式不正确", wordKey);
                    }
                    SyncToolsFnHandler conditionHandler = new SyncConditionFncHandlerWrapper.ConditionHandler("CONDITION:ELSE") {
                        @Override
                        public boolean condition(ParamWrapper params) {
                            return true;
                        }
                    };
                    int endIndex = findBlockEndIndex(list, start + 1, end);
                    List<SyncFnHandler> childers = handleListBuildSync(list, start + 2, endIndex - 1);
                    conditionHandler.addChildren(childers);
                    wrapper.addChildren(conditionHandler);
                    start = endIndex + 1;
                    break;
                default:
                    return start;
            }
        }
        return start;
    }
}
