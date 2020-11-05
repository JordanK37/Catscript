package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci466.parser.expressions.Expression;
import org.objectweb.asm.Opcodes;

import java.util.LinkedList;
import java.util.List;

import static edu.montana.csci.csci466.bytecode.ByteCodeGenerator.internalNameFor;

public class CatScriptProgram extends Statement {

    private StringBuffer output = new StringBuffer();
    private List<Statement> statements = new LinkedList<>();
    private Expression expression;

    public void print(Object v) {
        output.append(v);
    }

    public String getOutput(){
        return output.toString();
    }

    public void addStatement(Statement child) {
        statements.add(addChild(child));
    }

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    @Override
    public void execute() {
        if (expression != null) {
            print(expression.evaluate());
        } else {
            for (Statement statement : statements) {
                statement.execute();
            }
        }
    }

    @Override
    public void compileToBytecode(ByteCodeGenerator code) {
        if (isExpression()) {
            code.addVarInstruction(Opcodes.ALOAD, 0);
            getExpression().compileToBytecode(code);
            code.addMethodInstruction(Opcodes.INVOKESTATIC, internalNameFor(Integer.class),
                    "valueOf", "(I)Ljava/lang/Integer;", false);
            code.addMethodInstruction(Opcodes.INVOKEVIRTUAL, internalNameFor(CatScriptProgram.class),
                    "print", "(Ljava/lang/Object;)V", false);
            code.addInstruction(Opcodes.RETURN);
        } else {
            for (Statement statement : statements) {
                statement.compileToBytecode(code);
            }
        }
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean isExpression() {
        return expression != null;
    }
}
