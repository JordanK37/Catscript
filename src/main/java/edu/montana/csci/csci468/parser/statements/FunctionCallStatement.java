package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.Expression;
import edu.montana.csci.csci468.parser.expressions.FunctionCallExpression;

import java.util.List;

public class FunctionCallStatement extends Statement {
    private FunctionCallExpression expression;
    public FunctionCallStatement(FunctionCallExpression parseExpression) {
        this.expression = addChild(parseExpression);
    }

    public List<Expression> getArguments() {
        return expression.getArguments();
    }

    public String getName() {
        return expression.getName();
    }
}
