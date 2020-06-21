package org.mintflow.parsing.mark;

import java.util.HashMap;
import java.util.Map;

public class KeyworkItem extends Word {

    public KeyworkItem() {
        super();
    }

    public KeyworkItem(String value, WordType wordType) {
        this.setType(wordType);
        this.setValue(value);
    }

    private static Map<String, KeyworkItem> keyworkItemMap = new HashMap<>();

    static {
        keyworkItemMap.put("namespace", new KeyworkItem("namespace", WordType.NAMESPACE));
        keyworkItemMap.put("process", new KeyworkItem("process", WordType.PROCESS));
        keyworkItemMap.put("handle", new KeyworkItem("handle", WordType.HANDLE));
        keyworkItemMap.put("reorder", new KeyworkItem("reorder", WordType.REORDER_HANDLE));
        keyworkItemMap.put("cycle", new KeyworkItem("cycle", WordType.CYCLE_HANDLE));
        keyworkItemMap.put("if", new KeyworkItem("if", WordType.CONDITION_IF_HANDLE));
        keyworkItemMap.put("elif", new KeyworkItem("else if", WordType.CONDITION_ELSE_IF_HANDLE));
        keyworkItemMap.put("else", new KeyworkItem("else", WordType.CONDITION_ELSE_HANDLE));
        keyworkItemMap.put("(", new KeyworkItem("(", WordType.NAME_SPLIT_START_KEY));
        keyworkItemMap.put(")", new KeyworkItem(")", WordType.NAME_SPLIT_END_KEY));
        keyworkItemMap.put("{", new KeyworkItem("{", WordType.BLOCKE_START_KEY));
        keyworkItemMap.put("}", new KeyworkItem("}", WordType.BLOCKE_END_KEY));
        keyworkItemMap.put("sync",new KeyworkItem("sync",WordType.SYNC));
        keyworkItemMap.put("async",new KeyworkItem("async",WordType.ASYNC));
        keyworkItemMap.put(":",new KeyworkItem(":",WordType.SPLICE_I));
        keyworkItemMap.put("->", new KeyworkItem("->", WordType.HANDLE_LINK_KEY));
    }

    public static boolean isNameStartKey(String key) {
        return "(".equals(key);
    }

    public static boolean isNameEndKey(String key) {
        return ")".equals(key);
    }

    public static boolean isNameStartKey(char key) {
        return '(' == key;
    }

    public static boolean isNameEndKey(char key) {
        return ')' == key;
    }

    public static KeyworkItem getKeyWorkItem(String key) {
        return keyworkItemMap.get(key);
    }
}
