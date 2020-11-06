package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.Expression;

public class AssignmentStatement extends Statement {
    private Expression expression;
    private String variableName;

    public void setExpression(String variableName, Expression parseExpression) {
        this.variableName = variableName;
        this.expression = addChild(parseExpression);
    }

}
