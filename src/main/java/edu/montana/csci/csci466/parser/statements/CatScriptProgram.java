package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.ParseElement;
import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.parser.expressions.Expression;
import edu.montana.csci.csci466.tokenizer.Token;

import java.util.LinkedList;
import java.util.List;

public class CatScriptProgram extends ParseElement implements Statement {

    private StringBuffer output = new StringBuffer();
    private List<Statement> statements = new LinkedList<>();
    private Expression expression;

    public CatScriptProgram(Token start, Token end) {
        super(start, end);
    }

    public void print(Object v) {
        output.append(v);
    }

    public String getOutput(){
        return output.toString();
    }

    @Override
    public void setParent(Statement statement) {
        throw new IllegalArgumentException("Programs cannot have parents");
    }

    public void addStatement(Statement child) {
        child.setParent(this);
        statements.add(child);
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

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);
    }
}
