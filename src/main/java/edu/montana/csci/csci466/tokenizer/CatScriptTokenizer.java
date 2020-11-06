package edu.montana.csci.csci466.tokenizer;

import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.montana.csci.csci466.tokenizer.TokenType.*;

public class CatScriptTokenizer {

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    static {
        KEYWORDS.put("print", PRINT);
    }

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
        if (scanSyntax()) {
            return;
        }
        addToken(ERROR, "<Unexpected Token: [" + peek() + "]>");
        postion++;
    }

    private boolean scanString() {
        // TODO implement
        return false;
    }

    private boolean scanIdentifier() {
        if( isAlpha(peek())) {
            int start = postion;
            while (isAlphaNumeric(peek())) {
                postion++;
            }
            String value = src.substring(start, postion);
            if (KEYWORDS.containsKey(value)) {
                addToken(KEYWORDS.get(value), value);
            } else {
                addToken(IDENTIFIER, value);
            }
            return true;
        } else {
            return false;
        }
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
        if(c == '-') {
            addToken(MINUS, "-");
            return true;
        };
        if(c == '(') {
            addToken(LEFT_PAREN, "(");
            return true;
        };
        if(c == ')') {
            addToken(RIGHT_PAREN, ")");
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
        Token token = getCurrentToken();
        return token.getType().equals(IDENTIFIER) && token.getStringValue().equals(keyword);
    }

    public Token getCurrentToken() {
        if (currentToken < tokens.size()) {
            return tokens.get(currentToken);
        } else {
            return Token.EOF_TOKEN;
        }
    }

    public Token consumeToken() {
        return tokens.get(currentToken++);
    }

    public boolean match(TokenType... type) {
        for (TokenType tokenType : type) {
            if (getCurrentToken().getType().equals(tokenType)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        currentToken = 0;
    }

    public boolean hasMoreTokens() {
        return !getCurrentToken().getType().equals(EOF);
    }

    public Token lastToken() {
        return tokens.get(Math.max(0, currentToken - 1));
    }
}