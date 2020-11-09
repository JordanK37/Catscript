package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.Expression;

public class ReturnStatement extends Statement {
    private Expression expression;
    private FunctionDefinitionStatement function;

    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }

    public void setFunctionDefinition(FunctionDefinitionStatement func) {
        this.function = func;
    }

    public Expression getExpression() {
    public Expression getExpression() {
        return expression;
    }
}