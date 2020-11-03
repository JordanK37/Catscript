package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.tokenizer.Token;

public class SyntaxErrorExpression extends Expression {

    public SyntaxErrorExpression(Token start) {
        super(start);
        addError("Bad token : " + getStart());
    }

    @Override
    public Object evaluate() {
        throw new IllegalStateException("Bad token : " + getStart());
    }

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);

    }
}
