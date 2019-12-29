package org.mekweg.parsing;

import org.mekweg.exception.ParsingException;
import org.mekweg.handle.Handler;
import org.mekweg.parsing.mark.Word;
import org.mekweg.parsing.mark.WordType;

import java.util.*;

public class FnEngineDataStructureTool extends Exception {

    public static Map<String, List<Handler>> runGrammarAnalysisTool(List<Word> list) {
        Map<String, List<Handler>> dataStructureMap = new HashMap<>();
        int start = 0;
        int end = list.size();
        while (start < end) {
            Word namespaceWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingException("当前未发现命名空间标记(namespace)", namespaceWord);
            }
            List<Handler> handlerList = new ArrayList<>();
            String name = findName(list, start + 1);
            dataStructureMap.put(name, handlerList);
            start = start + 4;
            Word blockStartWord = list.get(start);
            if (namespaceWord.getType() != WordType.NAMESPACE) {
                throw new ParsingException("当前未发现块起始字符'{'", blockStartWord);
            }
            int blockStartNum = 1;
            int namespaceEnd = start + 1;
            while (blockStartNum != 0 && namespaceEnd < end) {
                if(list.get(namespaceEnd).getType()==WordType.BLOCKE_START_KEY){
                    blockStartNum++;
                }else if(list.get(namespaceEnd).getType()==WordType.BLOCKE_END_KEY){
                    blockStartNum--;
                }
                namespaceEnd++;
            }
            if(namespaceEnd==end){
                throw new ParsingException("当前namespace块没有闭合,未发现对应的'}'字符", blockStartWord);
            }
            start = namespaceEnd+1;
            handlerList.addAll(processHandleListBuild(list,start+2,namespaceEnd-1));
        }
        return dataStructureMap;
    }

    private static String findName(List<Word> list, int start) {
        Word nameStartKey = list.get(start++);
        if (nameStartKey.getType() != WordType.NAME_SPLIT_START_KEY) {
            throw new ParsingException("当前未发命名起始标记'('", nameStartKey);
        }
        Word name = list.get(start++);
        if (name.getType() != WordType.VALUE) {
            throw new ParsingException("当前值命名错误,不可为关键字", name);
        }
        Word nameEndKey = list.get(start);
        if (nameEndKey.getType() != WordType.NAME_SPLIT_START_KEY) {
            throw new ParsingException("当前未发命名终止标记')'", nameEndKey);
        }
        return name.getValue();
    }

    private static List<Handler> processHandleListBuild(List<Word> list, int start, int end) {
        return new ArrayList<>();
    }

    private static List<Handler> handleListBuild(List<Word> list, int start, int end) {
        return null;
    }

    private static List<Handler> conditionHandleListBuild(List<Word> list, int start, int end) {
        return null;
    }

    private static List<Handler> reorderHandleListBuild(List<Word> list, int start, int end) {
        return null;
    }
}

