package org.mekweg.exception;

import org.mekweg.parsing.mark.Word;

public class ParsingException extends BaseRuntimeError {
    public ParsingException(String msg, Word word) {
        super("parsing error :" + msg+","+"line :"+(word.getLineNum()+1)+", index: "+(word.getWordStartNum()+1));
    }
}
