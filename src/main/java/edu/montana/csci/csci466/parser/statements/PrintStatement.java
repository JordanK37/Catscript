package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.expressions.Expression;

public class PrintStatement extends Statement {
    private Expression expression;

    @Override
    void execute() {
        getProgram().print(expression.evaluate());
    }

    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }
}
