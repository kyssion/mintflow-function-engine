package com.kyssion.galaxy.script.translater.data.workKeyData;

import java.util.HashSet;
import java.util.Set;

public class KeywordMap {
    private static Set<String> keyWordMap = new HashSet<>();
    static {
        keyWordMap.add("process");
        keyWordMap.add("handle");
        keyWordMap.add("namespace");
        keyWordMap.add("n");
    }

    public static boolean isKeyword(String keyWord){
        return keyWordMap.contains(keyWord);
    }
}
