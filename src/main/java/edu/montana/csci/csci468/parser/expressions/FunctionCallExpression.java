package edu.montana.csci.csci468.parser.expressions;

import java.util.LinkedList;
import java.util.List;

public class FunctionCallExpression extends Expression {
    List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.arguments = new LinkedList<>();
        for (Expression value : arguments) {
            this.arguments.add(addChild(value));
        }
    }

}
