package edu.montana.csci.csci468.tokenizer;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {
    // syntax
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SLASH, STAR,
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // literals
    IDENTIFIER, STRING, INTEGER,

    // keywords
    ELSE, FALSE, FUNCTION, FOR, IF, NULL,
    PRINT, RETURN, TRUE, VAR,

    ERROR,
    EOF;

    public static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    static {
        KEYWORDS.put("else", ELSE);
        KEYWORDS.put("false", FALSE);
        KEYWORDS.put("function", FUNCTION);
        KEYWORDS.put("for", FOR);
        KEYWORDS.put("if", IF);
        KEYWORDS.put("null", NULL);
        KEYWORDS.put("print", PRINT);
        KEYWORDS.put("return", RETURN);
        KEYWORDS.put("true", TRUE);
        KEYWORDS.put("var", VAR);
    }


}
