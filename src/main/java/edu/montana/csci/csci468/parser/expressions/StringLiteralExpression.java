package edu.montana.csci.csci468.parser.expressions;

public class StringLiteralExpression extends Expression {
    private final String stringValue;

    public StringLiteralExpression(String value) {
        this.stringValue = value;
    }

}
