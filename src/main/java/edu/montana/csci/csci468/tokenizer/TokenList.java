package edu.montana.csci.csci468.tokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static edu.montana.csci.csci468.tokenizer.TokenType.EOF;

public class TokenList implements Iterable<Token> {

    private final CatScriptTokenizer tokenizer;
    List<Token> tokens = new ArrayList<>();
    int currentToken = 0;

    public TokenList(CatScriptTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    void addToken(TokenType eof, String stringValue, int start, int end, int line, int lineOffset) {
        tokens.add(new Token(start, end, line, lineOffset, stringValue, eof));
    }

    public Token getCurrentToken() {
        return tokens.get(currentToken);
    }

    public Token consumeToken() {
        return tokens.get(currentToken++);
    }

    public boolean matchAndConsume(TokenType... type) {
        if (match(type)) {
            consumeToken();
            return true;
        } else {
            return false;
        }
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

    public Stream<Token> stream() {
        return tokens.stream();
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        tokens.forEach(action);
    }

    @Override
    public Spliterator<Token> spliterator() {
        return tokens.spliterator();
    }

}
