package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.tokenizer.Token;

public class SyntaxErrorStatement extends Statement {

    public SyntaxErrorStatement(Token start) {
        setToken(start);
        addError("Bad token : " + getStart());
    }

    @Override
    public void execute() {
        throw new IllegalStateException("Bad token : " + getStart());
    }

}
