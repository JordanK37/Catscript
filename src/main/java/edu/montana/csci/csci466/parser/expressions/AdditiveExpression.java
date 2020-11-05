package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci466.tokenizer.Token;
import edu.montana.csci.csci466.tokenizer.TokenType;
import org.objectweb.asm.Opcodes;

public class AdditiveExpression extends Expression {

    private final Token operator;
    private final boolean add;
    private final Expression leftHandSide;
    private final Expression rightHandSide;

    public AdditiveExpression(Token operator, Expression leftHandSide, Expression rightHandSide) {
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

    public Expression getLeftHandSide() {
        return leftHandSide;
    }

    public boolean isAdd() {
        return add;
    }

    public Expression getRightHandSide() {
        return rightHandSide;
    }

    @Override
    public void compileToBytecode(ByteCodeGenerator code) {
        getLeftHandSide().compileToBytecode(code);
        getRightHandSide().compileToBytecode(code);
        if (isAdd()) {
            code.addInstruction(Opcodes.IADD);
        } else {
            code.addInstruction(Opcodes.ISUB);
        }
    }
}
