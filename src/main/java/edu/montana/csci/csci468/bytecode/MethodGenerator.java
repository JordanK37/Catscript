package edu.montana.csci.csci468.bytecode;

import org.objectweb.asm.*;

public class MethodGenerator implements AutoCloseable {
    private final MethodVisitor delegate;

    public MethodGenerator(MethodVisitor delgate) {
        this.delegate = delgate;
    }

    @Override
    public void close() throws Exception {
        delegate.visitMaxs(0, 0);
        delegate.visitEnd();
    }


    // helper code

    // delegation code
    public void addInstruction(int opcode) {
        delegate.visitInsn(opcode);
    }

    public void addIntInstruction(int opcode, int operand) {
        delegate.visitIntInsn(opcode, operand);
    }

    public void addVarInstruction(int opcode, int var) {
        delegate.visitVarInsn(opcode, var);
    }

    public void addTypeInstruction(int opcode, String type) {
        delegate.visitTypeInsn(opcode, type);
    }

    public void addFieldInstruction(int opcode, String owner, String name, String descriptor) {
        delegate.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Deprecated
    public void addMethodInstruction(int opcode, String owner, String name, String descriptor) {
        delegate.visitMethodInsn(opcode, owner, name, descriptor);
    }

    public void addMethodInstruction(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        delegate.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    public void addJumpInstruction(int opcode, Label label) {
        delegate.visitJumpInsn(opcode, label);
    }

    public void addLabel(Label label) {
        delegate.visitLabel(label);
    }

    public void loadConstantValue(Object value) {
        delegate.visitLdcInsn(value);
    }

    public void addLineNumber(int line, Label start) {
        delegate.visitLineNumber(line, start);
    }
}
