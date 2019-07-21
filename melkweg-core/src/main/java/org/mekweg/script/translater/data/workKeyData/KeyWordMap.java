package org.mekweg.script.translater.data.workKeyData;

import java.util.HashSet;
import java.util.Set;

public class KeyWordMap {
    private  Set<String> keyWordMap = new HashSet<>();
    public KeyWordMap(){
        keyWordMap.add("process");
        keyWordMap.add("handle");
        keyWordMap.add("namespace");
        keyWordMap.add("n");
    }

    public boolean isKeyword(String keyWord){
        return keyWordMap.contains(keyWord);
    }
}
