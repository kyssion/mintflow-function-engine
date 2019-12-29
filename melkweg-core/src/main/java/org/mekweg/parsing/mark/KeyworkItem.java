package org.mekweg.parsing.mark;

import java.util.HashMap;
import java.util.Map;

public class KeyworkItem extends Word{

    public KeyworkItem(){
        super();
    }

    public KeyworkItem(String name,String value,WordType wordType){
        this.setName(name);
        this.setType(wordType);
        this.setValue(value);
    }

    private static Map<String,KeyworkItem>  keyworkItemMap = new HashMap<>();
    static {
        keyworkItemMap.put("namespace",new KeyworkItem("namesapce","namespace",WordType.STATEMENT_KEY_WORD));
        keyworkItemMap.put("process",new KeyworkItem("process","process",WordType.STATEMENT_KEY_WORD));
        keyworkItemMap.put("handle",new KeyworkItem("handle","handle",WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("reorder",new KeyworkItem("reorder","reorder",WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("if",new KeyworkItem("if","if",WordType.HANDLE_KEY_WORD));
        keyworkItemMap.put("elif",new KeyworkItem("elif","elif",WordType.OTHER_KEY_WORD));
        keyworkItemMap.put("else",new KeyworkItem("namesapce","namespace",WordType.OTHER_KEY_WORD));
        keyworkItemMap.put("(",new KeyworkItem("(","(",WordType.DELIMITER_KEY_WORD));
        keyworkItemMap.put(")",new KeyworkItem(")",")",WordType.DELIMITER_KEY_WORD));
        keyworkItemMap.put("{",new KeyworkItem("{","{",WordType.DELIMITER_KEY_WORD));
        keyworkItemMap.put("}",new KeyworkItem("}","}",WordType.DELIMITER_KEY_WORD));
    }
}
