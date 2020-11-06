package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.parser.ParseElement;

public abstract class Statement extends ParseElement {

    public void execute() {
        throw new UnsupportedOperationException("execute needs to be implemented for " + this.getClass().getName());
    }

    @Override
    public void transpile(StringBuilder javascript) {
        throw new UnsupportedOperationException("transpile needs to be implemented for " + this.getClass().getName());
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        throw new UnsupportedOperationException("compile needs to be implemented for " + this.getClass().getName());
    }

}
