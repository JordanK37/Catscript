package edu.montana.csci.csci466.js;

import edu.montana.csci.csci466.parser.ParseTreeVisitor;
import edu.montana.csci.csci466.parser.expressions.*;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.parser.statements.PrintStatement;
import edu.montana.csci.csci466.parser.statements.SyntaxErrorStatement;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;

public class JSTranspiler {

    private final CatScriptProgram program;
    private final String javascriptSource;

    public JSTranspiler(CatScriptProgram program) {
        this.program = program;
        this.javascriptSource = transpile(program);
    }

    public String evaluate() {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            ScriptContext context = engine.getContext();
            StringWriter writer = new StringWriter();
            context.setWriter(writer);
            engine.eval(javascriptSource);
            return writer.toString();
        } catch (ScriptException e) {
            return e.getMessage();
        }
    }

    private String transpile(CatScriptProgram program) {
        StringBuilder sb = new StringBuilder();
        if (program.getExpression() != null) {
            sb.append("print(");
            transpileExpression(sb, program.getExpression());
            sb.append(");\n");
        } else {
            // TODO implement statements
        }
        return sb.toString();
    }

    private void transpileExpression(StringBuilder buffer, Expression expression) {
        if (expression instanceof AdditiveExpression) {
            AdditiveExpression additiveExpression = (AdditiveExpression) expression;
            transpileExpression(buffer, additiveExpression.getLeftHandSide());
            buffer.append(additiveExpression.isAdd() ? " + " : " - ");
            transpileExpression(buffer, additiveExpression.getRightHandSide());
        } else if (expression instanceof IntegerLiteralExpression) {
            IntegerLiteralExpression integerLiteral = (IntegerLiteralExpression) expression;
            buffer.append(integerLiteral.evaluate());
        } else {
            throw new UnsupportedOperationException("Don't know how to transpile : " + expression);
        }
    }

    public String getJavascriptSource() {
        return javascriptSource;
    }
}
