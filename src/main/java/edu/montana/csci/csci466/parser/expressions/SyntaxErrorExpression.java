package edu.montana.csci.csci466.parser.expressions;

public class SyntaxErrorExpression extends Expression {

    public SyntaxErrorExpression() {
        addError("Bad token : " + getStart());
    }

    @Override
    public Object evaluate() {
        throw new IllegalStateException("Bad token : " + getStart());
    }

}
