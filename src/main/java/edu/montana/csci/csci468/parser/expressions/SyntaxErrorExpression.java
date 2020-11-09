package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.SymbolTable;

public class SyntaxErrorExpression extends Expression {

    public SyntaxErrorExpression() {
        addError("Bad token : " + getStart());
    }

    @Override
    public Object evaluate() {
        throw new IllegalStateException("Bad token : " + getStart());
    }

    @Override
    public CatscriptType getType() {
        return CatscriptType.OBJECT;
    }

    @Override
    public void validate(SymbolTable symbolTable) {}

}
