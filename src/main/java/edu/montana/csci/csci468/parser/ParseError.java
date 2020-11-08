package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.tokenizer.Token;

public class ParseError {

    public static final String UNTERMINATED_LIST = "Unterminated list literal";
    public static final String UNTERMINATED_ARG_LIST = "Unterminated argument list";

    private Token location;
    private String message;

    public ParseError(Token location, String message) {
        this.location = location;
        this.message = message;
    }

    public Token getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }
}
