package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.parser.expressions.Expression;
import org.objectweb.asm.Opcodes;

import java.util.LinkedList;
import java.util.List;

import static edu.montana.csci.csci468.bytecode.ByteCodeGenerator.internalNameFor;

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

    public Expression getExpression() {
        return expression;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean isExpression() {
        return expression != null;
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
    public void transpile(StringBuilder javascript) {
        if (isExpression()) {
            javascript.append("print(");
            expression.transpile(javascript);
            javascript.append(");\n");
        } else {
            for (Statement statement : statements) {
                statement.transpile(javascript);
            }
        }
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        if (isExpression()) {
            code.addVarInstruction(Opcodes.ALOAD, 0);
            getExpression().compile(code);
            code.addMethodInstruction(Opcodes.INVOKESTATIC, internalNameFor(Integer.class),
                    "valueOf", "(I)Ljava/lang/Integer;", false);
            code.addMethodInstruction(Opcodes.INVOKEVIRTUAL, internalNameFor(CatScriptProgram.class),
                    "print", "(Ljava/lang/Object;)V", false);
            code.addInstruction(Opcodes.RETURN);
        } else {
            for (Statement statement : statements) {
                statement.compile(code);
            }
        }
    }

}
