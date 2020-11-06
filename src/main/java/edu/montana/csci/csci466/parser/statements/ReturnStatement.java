package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.expressions.Expression;

public class ReturnStatement extends Statement {
    private Expression expression;

    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }

}