package org.melkweg.exception;

import org.melkweg.parsing.mark.Word;

/**
 * Syntax detection run-time exception
 */
public class ParsingRuntimeError extends BaseRuntimeError {
    public ParsingRuntimeError(String msg, Word word) {
        super("parsing error :" + msg+","+"line :"+(word.getLineNum()+1)+", index: "+(word.getWordStartNum()+1));
    }
}
