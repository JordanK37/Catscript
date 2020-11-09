package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.eval.CatscriptRuntime;
import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.ParseError;
import edu.montana.csci.csci468.parser.SymbolTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionDefinitionStatement extends Statement {
    private String name;
    private CatscriptType type;
    private List<CatscriptType> argumentTypes = new ArrayList<>();
    private List<String> argumentNames = new ArrayList<>();
    private LinkedList<Statement> body;

    public void setName(String name) {
        this.name = name;
    }

    public CatscriptType getType() {
        return type;
    }

    public void setType(CatscriptType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void addParameter(String name, CatscriptType type) {
        argumentNames.add(name);
        argumentTypes.add(type);
    }

    public String getParameterName(int i) {
        return argumentNames.get(i);
    }

    public CatscriptType getParameterType(int i) {
        return argumentTypes.get(i);
    }

    public int getParameterCount() {
        return argumentNames.size();
    }

    public void setBody(List<Statement> statements) {
        this.body = new LinkedList<>();
        for (Statement statement : statements) {
            this.body.add(addChild(statement));
        }
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        symbolTable.pushScope();
        for (int i = 0; i < getParameterCount(); i++) {
            if (symbolTable.hasSymbol(getParameterName(i))) {
                addError(ParseError.DUPLICATE_NAME);
            } else {
                symbolTable.registerSymbol(getParameterName(i), getParameterType(i));
            }
        }
        for (Statement statement : body) {
            statement.validate(symbolTable);
        }
        symbolTable.popScope();
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
