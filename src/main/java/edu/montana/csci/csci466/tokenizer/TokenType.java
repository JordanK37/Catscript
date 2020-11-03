package edu.montana.csci.csci466.tokenizer;

public enum TokenType {
    // syntax
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
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
    EOF
}
