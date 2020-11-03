package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.tokenizer.Token;

public class StringLiteralExpression extends Expression {
    private Token value;

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);
    }
}
