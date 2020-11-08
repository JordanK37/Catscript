package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;

public class TypeLiteral extends Expression {

    private CatscriptType type;

    public void setType(CatscriptType type) {
        this.type = type;
    }

    public CatscriptType getType() {
        return type;
    }
}
