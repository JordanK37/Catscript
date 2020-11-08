package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.parser.statements.CatScriptProgram;
import edu.montana.csci.csci468.tokenizer.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class ParseElement {

    protected ParseElement parent;
    private Token start;
    private Token end;
    private List<ParseElement> children;
    private List<ParseError> errors;

    public ParseElement() {
        this.errors = new LinkedList<>();
        this.children = new LinkedList<>();
    }

    public CatScriptProgram getProgram() {
        if (this.getParent() instanceof CatScriptProgram) {
            return (CatScriptProgram) this.getParent();
        } else {
            return getParent().getProgram();
        }
    }

    public void setStart(Token start) {
        this.start = start;
    }

    public void setEnd(Token end) {
        this.end = end;
    }

    public void setToken(Token token) {
        setStart(token);
        setEnd(token);
    }

    public ParseElement getParent() {
        return parent;
    }

    public Token getStart() {
        return start;
    }

    public Token getEnd() {
        return end;
    }

    public List<ParseError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void addError(String errorMessage) {
        addError(errorMessage, getStart());
    }

    public void addError(String errorMessage, Token token) {
        errors.add(new ParseError(token, errorMessage));
    }

    protected <T extends ParseElement> T addChild(T element) {
        element.parent = this;
        children.add(element);
        return element;
    }

    public List<ParseElement> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public abstract void transpile(StringBuilder javascript);

    public abstract void compile(ByteCodeGenerator code);

    public boolean hasError(String errorMessage) {
        return errors.stream().anyMatch(parseError -> Objects.equals(parseError.getMessage(), errorMessage));
    }
}
