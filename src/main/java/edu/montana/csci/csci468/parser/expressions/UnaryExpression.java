package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.ParseError;
import edu.montana.csci.csci468.parser.SymbolTable;
import edu.montana.csci.csci468.tokenizer.Token;
import edu.montana.csci.csci468.tokenizer.TokenType;

public class UnaryExpression extends Expression {

    private final Token operator;
    private final Expression rightHandSide;

    public UnaryExpression(Token operator, Expression rightHandSide) {
        this.rightHandSide = addChild(rightHandSide);
        this.operator = operator;
    }

    public Expression getRightHandSide() {
        return rightHandSide;
    }

    public boolean isMinus() {
        return operator.getType().equals(TokenType.MINUS);
    }

    public boolean isNot() {
        return !isMinus();
    }

    @Override
    public String toString() {
        return super.toString() + "[" + operator.getStringValue() + "]";
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        if (isNot() && !rightHandSide.getType().equals(CatscriptType.BOOLEAN)) {
            addError(ParseError.INCOMPATIBLE_TYPES);
        } else if(isMinus() && !rightHandSide.getType().equals(CatscriptType.INT)) {
            addError(ParseError.INCOMPATIBLE_TYPES);
        }
    }

    @Override
    public CatscriptType getType() {
        if (isMinus()) {
            return CatscriptType.INT;
        } else {
            return CatscriptType.BOOLEAN;
        }
    }
}
