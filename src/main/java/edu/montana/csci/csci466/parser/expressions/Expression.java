package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.parser.ParseElement;
import edu.montana.csci.csci466.tokenizer.Token;

public abstract class Expression extends ParseElement {

    public Expression(Token start, Token end) {
        super(start, end);
    }

    public Expression(Token start) {
        super(start, start);
    }

    public Object evaluate() {
        return null;
    }
}
