package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.parser.expressions.Expression;

public class PrintStatement extends Statement {
    private Expression expression;

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    void execute() {
        getProgram().print(expression.evaluate());
    }

    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }
}
