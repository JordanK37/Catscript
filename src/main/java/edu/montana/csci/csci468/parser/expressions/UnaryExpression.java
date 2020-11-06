package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.tokenizer.Token;

public class UnaryExpression extends Expression {

    private final Token operator;
    private final Expression rightHandSide;

    public UnaryExpression(Token operator, Expression rightHandSide) {
        this.rightHandSide = addChild(rightHandSide);
        this.operator = operator;
    }

    public Expression getRightHandSide() {
        return rightHandSide;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + operator.getStringValue() + "]";
    }
}
