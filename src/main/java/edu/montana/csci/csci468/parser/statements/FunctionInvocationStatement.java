package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.FunctionCallExpression;

public class FunctionInvocationStatement extends Statement {
    private FunctionCallExpression expression;

    public FunctionInvocationStatement(FunctionCallExpression parseExpression) {
        this.expression = addChild(parseExpression);
    }

}
