package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.bytecode.ByteCodeGenerator;

public class IntegerLiteralExpression extends Expression {
    private final int integerVal;

    public IntegerLiteralExpression(String value) {
        this.integerVal = Integer.parseInt(value);
    }

    @Override
    public Object evaluate() {
        return integerVal;
    }

    @Override
    public String toString() {
        return integerVal + "";
    }

    @Override
    public void compileToBytecode(ByteCodeGenerator code) {
        code.loadConstantValue(integerVal);
    }
}
