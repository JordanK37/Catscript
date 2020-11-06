package edu.montana.csci.csci466.parser.expressions;

public class BooleanLiteralExpression extends Expression {
    private final boolean booleanValue;

    public BooleanLiteralExpression(boolean value) {
        this.booleanValue = value;
    }

}
