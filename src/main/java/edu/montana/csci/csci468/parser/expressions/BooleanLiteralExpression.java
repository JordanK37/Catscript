package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.SymbolTable;

public class BooleanLiteralExpression extends Expression {
    private final boolean booleanValue;

    public BooleanLiteralExpression(boolean value) {
        this.booleanValue = value;
    }

    public boolean getValue() {
        return booleanValue;
    }

    @Override
    public void validate(SymbolTable symbolTable) {}

    @Override
    public CatscriptType getType() {
        return CatscriptType.BOOLEAN;
    }
}
