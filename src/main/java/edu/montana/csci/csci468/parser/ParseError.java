package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.tokenizer.Token;

public class ParseError {

    private Token location;
    private String message;

    public ParseError(Token location, String message) {
        this.location = location;
        this.message = message;
    }



}
