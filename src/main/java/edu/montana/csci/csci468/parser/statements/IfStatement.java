package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IfStatement extends Statement {
    private Expression expression;
    private List<Statement> trueStatements = Collections.emptyList();
    private List<Statement> elseStatements = Collections.emptyList();

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    public List<Statement> getTrueStatements() {
        return trueStatements;
    }

    public void setTrueStatements(List<Statement> statements) {
        this.trueStatements = new LinkedList<>();
        for (Statement statement : statements) {
            this.trueStatements.add(addChild(statement));
        }
    }

    public List<Statement> getElseStatements() {
        return elseStatements;
    }

    public void setElseStatements(List<Statement> statements) {
        this.elseStatements = new LinkedList<>();
        for (Statement statement : statements) {
            this.elseStatements.add(addChild(statement));
        }
    }
}
