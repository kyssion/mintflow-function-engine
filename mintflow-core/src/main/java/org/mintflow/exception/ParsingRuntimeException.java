package org.mintflow.exception;

import org.mintflow.parsing.mark.Word;

/**
 * Syntax detection run-time exception
 */
public class ParsingRuntimeException extends BaseRuntimeException {
    public ParsingRuntimeException(String msg, Word word) {
        super("parsing error :" + msg+","+"line :"+(word.getLineNum()+1)+", index: "+(word.getWordStartNum()+1));
    }
}
