package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.ParseElement;
import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.parser.expressions.Expression;
import edu.montana.csci.csci466.tokenizer.Token;

import java.util.LinkedList;
import java.util.List;

public class CatScriptProgram extends Statement {

    private StringBuffer output = new StringBuffer();
    private List<Statement> statements = new LinkedList<>();
    private Expression expression;

    public void print(Object v) {
        output.append(v);
    }

    public String getOutput(){
        return output.toString();
    }

    public void addStatement(Statement child) {
        statements.add(addChild(child));
    }

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    @Override
    public void execute() {
        if (expression != null) {
            print(expression.evaluate());
        } else {
            for (Statement statement : statements) {
                statement.execute();
            }
        }
    }

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean isExpression() {
        return expression != null;
    }
}
