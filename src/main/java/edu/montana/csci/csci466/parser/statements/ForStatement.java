package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.expressions.Expression;

import java.util.LinkedList;
import java.util.List;

public class ForStatement extends Statement {
    private Expression expression;
    private String variableName;
    private List<Statement> body;

    public ForStatement(String variableName, Expression parseExpression, List<Statement> statements) {
        this.variableName = variableName;
        this.expression = addChild(parseExpression);
        this.body = new LinkedList<>();
        for (Statement statement : statements) {
            this.body.add(addChild(statement));
        }
    }

}
