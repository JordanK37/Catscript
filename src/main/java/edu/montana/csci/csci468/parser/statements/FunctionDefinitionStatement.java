package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.expressions.Expression;
import edu.montana.csci.csci468.parser.expressions.FunctionCallExpression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionDefinitionStatement extends Statement {
    private String name;
    private CatscriptType type;
    private List<CatscriptType> argumentTypes = new ArrayList<>();
    private List<String> argumentNames = new ArrayList<>();
    private LinkedList<Object> body;

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
}
