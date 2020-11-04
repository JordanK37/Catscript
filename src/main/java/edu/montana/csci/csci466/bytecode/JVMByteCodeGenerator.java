package edu.montana.csci.csci466.bytecode;

import edu.montana.csci.csci466.parser.expressions.AdditiveExpression;
import edu.montana.csci.csci466.parser.expressions.Expression;
import edu.montana.csci.csci466.parser.expressions.IntegerLiteralExpression;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.tree.analysis.Interpreter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;


public class JVMByteCodeGenerator {


    private static final AtomicInteger integer = new AtomicInteger();
    private static final DynamicClassLoader CLASS_LOADER = new DynamicClassLoader();

    private final CatScriptProgram program;

    public JVMByteCodeGenerator(CatScriptProgram program) {
        this.program = program;
    }

    public CatScriptProgram compileToBytecode() {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        String className = "edu/montana/csci/csci466/bytecode/CatScriptProgram" + integer.incrementAndGet();
        String dotClassName = className.replace('/', '.');

        classWriter.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC,
                className,
                null,
                "edu/montana/csci/csci466/parser/statements/CatScriptProgram", null);

        MethodVisitor constructor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "edu/montana/csci/csci466/parser/statements/CatScriptProgram", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();

        if (program.isExpression()) {
            MethodVisitor method = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", "()V", null, null);
            method.visitCode();
            method.visitVarInsn(Opcodes.ALOAD, 0);
            compileExpression(method, program.getExpression());
            method.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "edu/montana/csci/csci466/parser/statements/CatScriptProgram", "print", "(Ljava/lang/Object;)V", false);
            method.visitInsn(Opcodes.RETURN);
            method.visitMaxs(0, 0);
            method.visitEnd();
        }

        classWriter.visitEnd();

        byte[] classBytes = classWriter.toByteArray();

        printClassASM(classBytes);

        return loadClass(dotClassName, classBytes);
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

    private void compileExpression(MethodVisitor method, Expression expression) {
        if (expression instanceof IntegerLiteralExpression) {
            IntegerLiteralExpression integer = (IntegerLiteralExpression) expression;
            method.visitLdcInsn(integer.evaluate());
        } else if (expression instanceof AdditiveExpression) {
            AdditiveExpression additiveExpression = (AdditiveExpression) expression;
            compileExpression(method, additiveExpression.getLeftHandSide());
            compileExpression(method, additiveExpression.getRightHandSide());
            if (additiveExpression.isAdd()) {
                method.visitInsn(Opcodes.IADD);
            } else {
                method.visitInsn(Opcodes.ISUB);
            }
        }
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

}
