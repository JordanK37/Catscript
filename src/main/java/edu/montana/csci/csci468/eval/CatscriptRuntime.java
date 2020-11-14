package edu.montana.csci.csci468.eval;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// TODO - implement proper scoping
public class CatscriptRuntime {
    LinkedList<Map<String, Object>> scopes = new LinkedList<>();
    HashMap<String, Object> globalScope;

    public CatscriptRuntime(){
        globalScope = new HashMap<>();
    }

    public Object getValue(String name) {
        return globalScope.get(name);
    }

    public void setValue(String variableName, Object val) {
        globalScope.put(variableName, val);
    }

    public void pushScope() {
    }

    public void popScope() {
    }

}
