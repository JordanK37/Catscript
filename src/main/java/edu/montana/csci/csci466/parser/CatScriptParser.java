package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.parser.expressions.AdditiveExpression;
import edu.montana.csci.csci466.parser.expressions.Expression;
import edu.montana.csci.csci466.parser.expressions.IntegerLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.SyntaxErrorExpression;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.parser.statements.PrintStatement;
import edu.montana.csci.csci466.parser.statements.Statement;
import edu.montana.csci.csci466.parser.statements.SyntaxErrorStatement;
import edu.montana.csci.csci466.tokenizer.CatScriptTokenizer;
import edu.montana.csci.csci466.tokenizer.Token;
import edu.montana.csci.csci466.tokenizer.TokenType;

import static edu.montana.csci.csci466.tokenizer.TokenType.*;

public class CatScriptParser {

    CatScriptTokenizer tokenizer;

    public CatScriptProgram parse(String source) {
        tokenizer = new CatScriptTokenizer(source);

        // first parse an expression
        CatScriptProgram program = new CatScriptProgram();
        program.setStart(tokenizer.getCurrentToken());
        Expression expression = parseExpression();
        if (tokenizer.hasMoreTokens()) {
            tokenizer.reset();
            while (tokenizer.hasMoreTokens()) {
                program.addStatement(parseProgramStatement());
            }
        } else {
            program.setExpression(expression);
        }

        program.setEnd(tokenizer.getCurrentToken());
        return program;
    }

    //============================================================
    //  Statements
    //============================================================

    private Statement parseProgramStatement() {
        Statement printStmt = parsePrintStatement();
        if (printStmt != null) {
            return printStmt;
        }
        return new SyntaxErrorStatement(tokenizer.consumeToken());
    }

    private Statement parsePrintStatement() {
        if (tokenizer.match(PRINT)) {

            PrintStatement printStatement = new PrintStatement();
            printStatement.setStart(tokenizer.consumeToken());

            require(LEFT_PAREN, printStatement);
            printStatement.setExpression(parseExpression());
            printStatement.setEnd(require(RIGHT_PAREN, printStatement));

            return printStatement;
        } else {
            return null;
        }
    }

    //============================================================
    //  Expressions
    //============================================================

    private Expression parseExpression() {
        return parseAdditiveExpression();
    }

    private Expression parseAdditiveExpression() {
        Expression expression = parsePrimaryExpression();
        if (tokenizer.match(PLUS, MINUS)) {
            Token operator = tokenizer.consumeToken();
            final Expression rightHandSide = parseAdditiveExpression();
            AdditiveExpression additiveExpression = new AdditiveExpression(operator, expression, rightHandSide);
            return additiveExpression;
        } else {
            return expression;
        }
    }

    private Expression parsePrimaryExpression() {
        if (tokenizer.match(INTEGER)) {
            Token integerToken = tokenizer.consumeToken();
            IntegerLiteralExpression integerExpression = new IntegerLiteralExpression(integerToken.getStringValue());
            integerExpression.setToken(integerToken);
            return integerExpression;
        } else {
            SyntaxErrorExpression syntaxErrorExpression = new SyntaxErrorExpression();
            syntaxErrorExpression.setToken(tokenizer.consumeToken());
            return syntaxErrorExpression;
        }
    }

    //============================================================
    //  Parse Helpers
    //============================================================
    private Token require(TokenType type, ParseElement elt) {
        if(tokenizer.getCurrentToken().getType().equals(type)){
            return tokenizer.consumeToken();
        } else {
            elt.addError("Unexpected Token", tokenizer.getCurrentToken());
            return tokenizer.getCurrentToken();
        }
    }

}
