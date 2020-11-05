package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci466.parser.ParseElement;
import edu.montana.csci.csci466.tokenizer.Token;

public abstract class Expression extends ParseElement {
    public Object evaluate() {
        return null;
    }

    @Override
    public void compileToBytecode(ByteCodeGenerator code) {
        throw new UnsupportedOperationException("compileToBytecode needs to be implemented for " + this.getClass().getName());
    }
}
