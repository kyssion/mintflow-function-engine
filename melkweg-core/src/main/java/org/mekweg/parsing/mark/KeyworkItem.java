package org.mekweg.parsing.mark;

import java.util.HashMap;
import java.util.Map;

public class KeyworkItem extends Word {

    public KeyworkItem() {
        super();
    }

    public KeyworkItem(String name, String value, WordType wordType) {
        this.setName(name);
        this.setType(wordType);
        this.setValue(value);
    }

    private static Map<String, KeyworkItem> keyworkItemMap = new HashMap<>();

    static {
        keyworkItemMap.put("namespace", new KeyworkItem("namespace", "namespace", WordType.STATEMENT_KEY_WORD));
        keyworkItemMap.put("process", new KeyworkItem("process", "process", WordType.STATEMENT_KEY_WORD));
        keyworkItemMap.put("handle", new KeyworkItem("handle", "handle", WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("reorder", new KeyworkItem("reorder", "reorder", WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("if", new KeyworkItem("if", "if", WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("elif", new KeyworkItem("else if", "else if", WordType.OTHER_KEY_WORD));
        keyworkItemMap.put("else", new KeyworkItem("else", "namespace", WordType.OTHER_KEY_WORD));
        keyworkItemMap.put("(", new KeyworkItem("(", "(", WordType.NAME_SPLIT_START_KEY));
        keyworkItemMap.put(")", new KeyworkItem(")", ")", WordType.NAME_SPLIT_END_KEY));
        keyworkItemMap.put("{", new KeyworkItem("{", "{", WordType.BLOCKE_START_KEY));
        keyworkItemMap.put("}", new KeyworkItem("}", "}", WordType.BLOCKE_END_KEY));
        keyworkItemMap.put("->", new KeyworkItem("->", "->", WordType.HANDLE_LINK_KEY));
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

    public static KeyworkItem getKeyWorkItem(String key){
        return keyworkItemMap.get(key);
    }
}
