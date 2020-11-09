package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.SymbolTable;

public class StringLiteralExpression extends Expression {
    private final String stringValue;

    public StringLiteralExpression(String value) {
        this.stringValue = value;
    }

    public String getValue() {
        return stringValue;
    }

    @Override
    public CatscriptType getType() {
        return CatscriptType.STRING;
    }

    @Override
    public void validate(SymbolTable symbolTable) {}

}
