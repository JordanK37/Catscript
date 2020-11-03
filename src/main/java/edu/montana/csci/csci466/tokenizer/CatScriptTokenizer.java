package edu.montana.csci.csci466.tokenizer;

import java.util.ArrayList;
import java.util.List;

import static edu.montana.csci.csci466.tokenizer.TokenType.*;

public class CatScriptTokenizer {

    List<Token> tokens = new ArrayList<>();
    int currentToken = 0;

    String src;
    int postion = 0;
    int line = 1;
    int lineOffset = 0;

    public CatScriptTokenizer(String source) {
        src = source;
        tokenize();
    }

    private void tokenize() {
        consumeWhitespace();
        while (!isAtEnd()) {
            scanToken();
            consumeWhitespace();
        }
        addToken(EOF, "<EOF>");
    }

    private void scanToken() {
        if(scanNumber()) {
            return;
        }
        if(scanString()) {
            return;
        }
        if(scanIdentifier()) {
            return;
        }
        if(scanKeyword()) {
            return;
        }
        if (scanSyntax()) {
            return;
        }
        Token token = consumeToken();
        addToken(ERROR, "<Unexpected Token: [" + token + "]>");
    }

    private boolean scanString() {
        // TODO implement
        return false;
    }

    private boolean scanIdentifier() {
        // TODO implement
        return false;
    }

    private boolean scanKeyword() {
        // TODO implement
        return false;
    }

    private boolean scanNumber() {
        if( isDigit(peek())) {
            int start = postion;
            while (isDigit(peek())) {
                postion++;
            }
            addToken(INTEGER, src.substring(start, postion));
            return true;
        } else {
            return false;
        }
    }

    private boolean scanSyntax() {
        char c = takeToken();

        if(c == '+') {
            addToken(PLUS, "+");
            return true;
        };

        return false;
    }

    private void consumeWhitespace() {
        // TODO update line and lineOffsets
        while (!isAtEnd()) {
            char peek = peek();
            if (peek == ' ' || peek == '\r' || peek == '\t') {
                postion++;
                continue;
            } else if (peek == '\n') {
                postion++;
                continue;
            }
            break;
        }
    }

    //===============================================================
    // Utility functions
    //===============================================================

    private char peek() {
        if (isAtEnd()) return '\0';
        return src.charAt(postion);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private char takeToken() {
        char c = src.charAt(postion);
        postion = postion + 1;
        return c;
    }

    private boolean isAtEnd() {
        return postion >= src.length();
    }

    private void addToken(TokenType eof, String stringValue) {
        tokens.add(newToken(eof, stringValue));
    }

    private Token newToken(TokenType type, String stringValue) {
        return new Token(postion, postion, line, lineOffset, stringValue, type);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public boolean matchKeyword(String keyword) {
        Token token = currentToken();
        return token.getType().equals(IDENTIFIER) && token.getStringValue().equals(keyword);
    }

    public Token currentToken() {
        if (currentToken < tokens.size()) {
            return tokens.get(currentToken);
        } else {
            return Token.EOF_TOKEN;
        }
    }

    public Token consumeToken() {
        return tokens.get(currentToken++);
    }

    public void require(TokenType type) {
        throw new UnsupportedOperationException();
    }

    public boolean match(TokenType... type) {
        for (TokenType tokenType : type) {
            if (currentToken().getType().equals(tokenType)) {
                return true;
            }
        }
        return false;
    }
}