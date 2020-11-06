package edu.montana.csci.csci468.bytecode;

import edu.montana.csci.csci468.parser.statements.CatScriptProgram;
import org.objectweb.asm.*;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;


public class ByteCodeGenerator {

    private ClassWriter classWriter;
    private MethodGenerator executeMethod;

    private static final AtomicInteger integer = new AtomicInteger();
    private static final DynamicClassLoader CLASS_LOADER = new DynamicClassLoader();

    private final CatScriptProgram program;

    public ByteCodeGenerator(CatScriptProgram program) {
        this.program = program;
    }

    public CatScriptProgram compileToBytecode() throws Exception {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        String className = "edu/montana/csci/csci466/bytecode/CatScriptProgram" + integer.incrementAndGet();
        String dotClassName = className.replace('/', '.');
        classWriter.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC,
                className,
                null,
                "edu/montana/csci/csci466/parser/statements/CatScriptProgram", null);

        try (MethodGenerator constructor = makeMethod(Opcodes.ACC_PUBLIC, "<init>", "()V")) {
            constructor.addVarInstruction(Opcodes.ALOAD, 0);
            constructor.addMethodInstruction(Opcodes.INVOKESPECIAL, internalNameFor(CatScriptProgram.class), "<init>", "()V", false);
            constructor.addInstruction(Opcodes.RETURN);
        }
        executeMethod = makeMethod(Opcodes.ACC_PUBLIC, "execute", "()V");
        program.compile(this);
        executeMethod.close();

        classWriter.visitEnd();
        byte[] classBytes = classWriter.toByteArray();
        printClassASM(classBytes);
        return loadClass(dotClassName, classBytes);
    }

    public static String internalNameFor(Class clazz) {
        final String name = clazz.getName();
        return name.replace(".", "/");
    }

    private MethodGenerator makeMethod(int access, String name, String descriptor) {
        MethodVisitor method = classWriter.visitMethod(access, name, descriptor, null, null);
        method.visitCode();
        return new MethodGenerator(method);
    }

    private void printClassASM(byte[] classBytes) {
        StringWriter writer = new StringWriter();
        var visitor = new TraceClassVisitor(new PrintWriter(writer));
        CheckClassAdapter checkAdapter = new CheckClassAdapter(visitor);
        ClassReader reader = new ClassReader(classBytes);
        reader.accept(checkAdapter, 0);
        String decompiledTransformedClass = writer.getBuffer().toString();
        System.out.println(" JVM Bytecode ===================================\n");
        System.out.println(decompiledTransformedClass);
        System.out.println("\n ================================================");
    }

    private CatScriptProgram loadClass(String dotClassName, byte[] classBytes) {
        try {
            CLASS_LOADER.defineClass(dotClassName, classBytes);
            Class<?> clazz = CLASS_LOADER.loadClass(dotClassName);
            return (CatScriptProgram) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class DynamicClassLoader extends ClassLoader {
        public void defineClass(String name, byte[] bytes) {
            defineClass(name, bytes, 0, bytes.length);
        }
        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }
    }

    public void addInstruction(int opcode) {
        executeMethod.addInstruction(opcode);
    }

    public void addIntInstruction(int opcode, int operand) {
        executeMethod.addIntInstruction(opcode, operand);
    }

    public void addVarInstruction(int opcode, int var) {
        executeMethod.addVarInstruction(opcode, var);
    }

    public void addTypeInstruction(int opcode, String type) {
        executeMethod.addTypeInstruction(opcode, type);
    }

    public void addFieldInstruction(int opcode, String owner, String name, String descriptor) {
        executeMethod.addFieldInstruction(opcode, owner, name, descriptor);
    }

    @Deprecated
    public void addMethodInstruction(int opcode, String owner, String name, String descriptor) {
        executeMethod.addMethodInstruction(opcode, owner, name, descriptor);
    }

    public void addMethodInstruction(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        executeMethod.addMethodInstruction(opcode, owner, name, descriptor, isInterface);
    }

    public void addJumpInstruction(int opcode, Label label) {
        executeMethod.addJumpInstruction(opcode, label);
    }

    public void addLabel(Label label) {
        executeMethod.addLabel(label);
    }

    public void loadConstantValue(Object value) {
        executeMethod.loadConstantValue(value);
    }

    public void addLineNumber(int line, Label start) {
        executeMethod.addLineNumber(line, start);
    }
}
