package edu.montana.csci.csci468.parser.expressions;

public class IdentifierExpression extends Expression {
    private final String name;

    public IdentifierExpression(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }
}
