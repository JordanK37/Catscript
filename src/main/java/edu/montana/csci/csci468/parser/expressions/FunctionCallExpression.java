package edu.montana.csci.csci468.parser.expressions;

import java.util.LinkedList;
import java.util.List;

public class FunctionCallExpression extends Expression {
    private final String name;
    List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.arguments = new LinkedList<>();
        for (Expression value : arguments) {
            this.arguments.add(addChild(value));
        }
        this.name = functionName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public String getName() {
        return name;
    }
}
