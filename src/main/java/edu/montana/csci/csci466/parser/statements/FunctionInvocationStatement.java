package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.expressions.FunctionCallExpression;

public class FunctionInvocationStatement extends Statement {
    private FunctionCallExpression expression;

    public FunctionInvocationStatement(FunctionCallExpression parseExpression) {
        this.expression = addChild(parseExpression);
    }

}
