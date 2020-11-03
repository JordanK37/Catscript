package edu.montana.csci.csci466.tokenizer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static edu.montana.csci.csci466.tokenizer.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

public class CatScriptTokenizerTest {

    @Test
    public void basicTokenizerTest(){
        assertTokensAre("1 + 1", INTEGER, PLUS, INTEGER, EOF);
        assertTokensAre("1   +   1", INTEGER, PLUS, INTEGER, EOF);
        assertTokensAre("1  \n +  \n 1", INTEGER, PLUS, INTEGER, EOF);
    }

    private void assertTokensAre(String src, TokenType... expected) {
        CatScriptTokenizer tokenizer = new CatScriptTokenizer(src);
        List<Token> tokens = tokenizer.getTokens();
        assertEquals(Arrays.asList(expected), tokens.stream().map(Token::getType).collect(Collectors.toList()));
    }


}
