package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci466.parser.ParseElement;

public abstract class Statement extends ParseElement {
    abstract void execute();

    @Override
    public void compileToBytecode(ByteCodeGenerator code) {
        throw new UnsupportedOperationException("compileToBytecode needs to be implemented for " + this.getClass().getName());
    }
}
