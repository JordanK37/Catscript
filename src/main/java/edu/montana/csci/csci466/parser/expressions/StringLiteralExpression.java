package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.tokenizer.Token;

public class StringLiteralExpression extends Expression {
    private final String stringValue;

    public StringLiteralExpression(String value) {
        this.stringValue = value;
    }

}
