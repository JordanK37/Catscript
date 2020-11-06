package edu.montana.csci.csci468.parser.expressions;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;

public class IntegerLiteralExpression extends Expression {
    private final int integerVal;

    public IntegerLiteralExpression(String value) {
        this.integerVal = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return integerVal + "";
    }

    @Override
    public Object evaluate() {
        return integerVal;
    }

    @Override
    public void transpile(StringBuilder javascript) {
        javascript.append(integerVal);
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        code.loadConstantValue(integerVal);
    }
}
