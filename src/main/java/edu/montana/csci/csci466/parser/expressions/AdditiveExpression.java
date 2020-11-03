package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.tokenizer.Token;
import edu.montana.csci.csci466.tokenizer.TokenType;

public class AdditiveExpression extends Expression {

    private final Token operator;
    private final boolean add;
    private final Expression leftHandSide;
    private final Expression rightHandSide;

    public AdditiveExpression(Token operator, Expression leftHandSide, Expression rightHandSide) {
        super(leftHandSide.getStart(), rightHandSide.getEnd());
        this.leftHandSide = addChild(leftHandSide);
        this.rightHandSide = addChild(rightHandSide);
        this.operator = operator;
        this.add = operator.getType() == TokenType.PLUS;
    }

    @Override
    public Object evaluate() {
        Integer lhsValue = (Integer) leftHandSide.evaluate();
        Integer rhsValue = (Integer) rightHandSide.evaluate();
        if (add) {
            return lhsValue + rhsValue;
        } else {
            return lhsValue - rhsValue;
        }
    }

    @Override
    public void accept(ParseTreeVisitor visitor) {
        visitor.visit(this);
    }

}
