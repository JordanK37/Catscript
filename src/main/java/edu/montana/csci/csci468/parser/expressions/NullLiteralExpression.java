package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.SymbolTable;

public class NullLiteralExpression extends Expression {

    @Override
    public CatscriptType getType() {
        return CatscriptType.NULL;
    }

    @Override
    public void validate(SymbolTable symbolTable) {}
}
