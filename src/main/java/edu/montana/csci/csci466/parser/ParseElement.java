package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.tokenizer.Token;
import org.apache.velocity.runtime.directive.Parse;

import java.util.LinkedList;
import java.util.List;

public abstract class ParseElement {

    protected ParseElement parent;
    private Token start;
    private Token end;
    private List<ParseElement> children;
    private List<ParseError> errors;

    public ParseElement(Token start, Token end) {
        this.start = start;
        this.end = end;
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
        errors.add(new ParseError(getStart(), errorMessage));
    }

    protected <T extends ParseElement> T addChild(T element) {
        element.parent = this;
        children.add(element);
        return element;
    }

    abstract public void accept(ParseTreeVisitor visitor);

    public List<ParseElement> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
