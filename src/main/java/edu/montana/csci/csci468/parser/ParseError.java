package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.tokenizer.Token;

public class ParseError {

    public static final String UNTERMINATED_LIST = "Unterminated list literal";
    public static final String UNTERMINATED_ARG_LIST = "Unterminated argument list";
    public static final String BAD_TYPE_NAME = "Bad Type Name";
    public static final String DUPLICATE_NAME = "This name is already used in this program";
    public static final String INCOMPATIBLE_TYPES = "Incompatible types";
    public static final String UNKNOWN_NAME = "This name does not exit in this program";
    public static final String ARG_MISMATCH = "Wrong number of arguments";
    public static final String MISSING_RETURN_STATEMENT = "Missing return statement in function";

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
