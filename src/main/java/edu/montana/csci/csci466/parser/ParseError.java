package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.tokenizer.Token;

public class ParseError {

    private Token location;
    private String message;

    public ParseError(Token location, String message) {
        this.location = location;
        this.message = message;
    }



}
