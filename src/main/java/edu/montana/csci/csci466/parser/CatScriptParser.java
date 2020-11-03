package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.parser.expressions.AdditiveExpression;
import edu.montana.csci.csci466.parser.expressions.Expression;
import edu.montana.csci.csci466.parser.expressions.IntegerLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.SyntaxErrorExpression;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.tokenizer.CatScriptTokenizer;
import edu.montana.csci.csci466.tokenizer.Token;

import static edu.montana.csci.csci466.tokenizer.TokenType.*;

public class CatScriptParser {

    CatScriptTokenizer tokenizer;

    public CatScriptProgram parse(String source) {
        tokenizer = new CatScriptTokenizer(source);
        Token start = tokenizer.currentToken();
        Expression expression = parseExpression();
        Token end = tokenizer.currentToken();

        CatScriptProgram catScriptProgram = new CatScriptProgram(start, end);
        catScriptProgram.setExpression(expression);
        return catScriptProgram;
    }

    private Expression parseExpression() {
        return parseAdditiveExpression();
    }

    private Expression parseAdditiveExpression() {
        Expression expression = parsePrimaryExpression();
        while (tokenizer.match(PLUS, MINUS)) {
            Token operator = tokenizer.consumeToken();
            Expression rightHandSide = parsePrimaryExpression();
            expression = new AdditiveExpression(operator, expression, rightHandSide);
        }
        return expression;
    }

    private Expression parsePrimaryExpression() {
        if (tokenizer.match(INTEGER)) {
            return new IntegerLiteralExpression(tokenizer.consumeToken());
        } else {
            return new SyntaxErrorExpression(tokenizer.consumeToken());
        }
    }

}
