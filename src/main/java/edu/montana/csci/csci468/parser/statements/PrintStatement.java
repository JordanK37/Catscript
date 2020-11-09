package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.SymbolTable;
import edu.montana.csci.csci468.parser.expressions.Expression;

public class PrintStatement extends Statement {
    private Expression expression;

    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }

    @Override
    public void execute() {
        getProgram().print(expression.evaluate());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        expression.validate(symbolTable);
    }

}
