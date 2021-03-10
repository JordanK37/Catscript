package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.eval.CatscriptRuntime;
import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.ErrorType;
import edu.montana.csci.csci468.parser.ParseError;
import edu.montana.csci.csci468.parser.SymbolTable;
import edu.montana.csci.csci468.parser.expressions.Expression;

public class AssignmentStatement extends Statement {
    private Expression expression;
    private String variableName;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        expression.validate(symbolTable);
        CatscriptType symbolType = symbolTable.getSymbolType(getVariableName());
        if (symbolType == null) {
            addError(ErrorType.UNKNOWN_NAME);
        } else {
            // TOOD - verify compatilibity of types
        }
    }

    //==============================================================
    // Implementation
    //==============================================================
    @Override
    public void execute(CatscriptRuntime runtime) {
        super.execute(runtime);
    }

    @Override
    public void transpile(StringBuilder javascript) {
        super.transpile(javascript);
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        super.compile(code);
    }
}
