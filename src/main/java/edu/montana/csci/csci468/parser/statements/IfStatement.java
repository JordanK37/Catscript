package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.expressions.Expression;

import java.util.LinkedList;
import java.util.List;

public class IfStatement extends Statement {
    private Expression expression;
    private List<Statement> trueStatements;
    private List<Statement> elseStatements;

    public IfStatement(Expression parseExpression, List<Statement> trueStatements, List<Statement> elseStatements) {
        this.expression = addChild(parseExpression);
        this.trueStatements = new LinkedList<>();
        for (Statement statement : trueStatements) {
            this.trueStatements.add(addChild(statement));
        }
        this.elseStatements = new LinkedList<>();
        for (Statement statement : trueStatements) {
            this.elseStatements.add(addChild(statement));
        }
    }

}
