package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.parser.expressions.*;
import edu.montana.csci.csci468.parser.statements.*;
import edu.montana.csci.csci468.tokenizer.CatScriptTokenizer;
import edu.montana.csci.csci468.tokenizer.Token;
import edu.montana.csci.csci468.tokenizer.TokenList;
import edu.montana.csci.csci468.tokenizer.TokenType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static edu.montana.csci.csci468.tokenizer.TokenType.*;

public class CatScriptParser {

    private TokenList tokens;
    private FunctionDefinitionStatement currentFunctionDefinition;

    public CatScriptProgram parse(String source) {
        tokens = new CatScriptTokenizer(source).getTokens();

        // first parse an expression
        CatScriptProgram program = new CatScriptProgram();
        program.setStart(tokens.getCurrentToken());
        Expression expression = parseExpression();
        if (tokens.hasMoreTokens()) {
            tokens.reset();
            while (tokens.hasMoreTokens()) {
                program.addStatement(parseProgramStatement());
            }
        } else {
            program.setExpression(expression);
        }

        program.setEnd(tokens.getCurrentToken());
        return program;
    }

    public CatScriptProgram parseAsExpression(String source) {
        tokens = new CatScriptTokenizer(source).getTokens();
        CatScriptProgram program = new CatScriptProgram();
        program.setStart(tokens.getCurrentToken());
        Expression expression = parseExpression();
        program.setExpression(expression);
        program.setEnd(tokens.getCurrentToken());
        return program;
    }

    //============================================================
    //  Statements
    //============================================================

    private Statement parseProgramStatement() {
        Statement statement = parseStatement();
        if (statement != null) {
            return statement;
        }
        statement = parseFunctionDeclaration();
        return new SyntaxErrorStatement(tokens.consumeToken());
    }

    private Statement parseFunctionDeclaration() {
        return null;
    }

    private Statement parseStatement() {
        if (tokens.match(PRINT)) {
            return parsePrintStatement();
        }
        if (tokens.match(FOR)){
            return parseForStatement();
        }
        if (tokens.match(IF)){
            return parseIfStatement();
        }
        if (tokens.match(VAR)){
            return parseVarStatement();
        }
        if (tokens.matchAndConsume(IDENTIFIER)){
            if(tokens.matchAndConsume(LEFT_PAREN)){

            }
            return null;
        }
        if (tokens.match(RETURN)){
            return parseReturnStatement();
        }
        else{
            return null;
        }
    }

    private Statement parseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement();
        if(tokens.matchAndConsume(RETURN)){
            Token start = tokens.getCurrentToken();
            List<Expression> expression = new LinkedList<>();
            while (!tokens.matchAndConsume(EOF)) {
                Expression expression1 = parseExpression();
                expression.add(expression1);
            }
            returnStatement.setExpression((Expression) expression);
            returnStatement.setStart(start);
            returnStatement.setEnd(tokens.getCurrentToken());
        }
        return returnStatement;
    }

    private Statement parseVarStatement() {
        VariableStatement variableStatement = new VariableStatement();
        Token start = tokens.consumeToken();
        require(LEFT_PAREN, variableStatement);
        Token loopIdentifier = require(IDENTIFIER, variableStatement);
        CatscriptType explicittype = null;
        if(tokens.matchAndConsume(COLON)){
            explicittype = CatscriptType.OBJECT;
            variableStatement.setExplicitType(explicittype);
        }
        require(COLON, variableStatement);
        List<Expression> expression = new LinkedList<>();
        while (!tokens.matchAndConsume(RIGHT_PAREN, EOF)) {
            Expression expression1 = parseExpression();
            expression.add(expression1);
        }
        variableStatement.setExpression((Expression) expression);
        variableStatement.setStart(start);
        variableStatement.setVariableName(loopIdentifier.getStringValue());
        variableStatement.setEnd(tokens.getCurrentToken());
        return variableStatement;
    }

    private Statement parseIfStatement() {
        IfStatement ifStatement = new IfStatement();
        Token start = tokens.consumeToken();
        require(LEFT_PAREN, ifStatement);
        List<Expression> expression = new LinkedList<>();
        while (!tokens.matchAndConsume(RIGHT_PAREN, EOF)) {
            Expression expression1 = parseExpression();
            expression.add(expression1);
        }
        ifStatement.setExpression((Expression) expression);
        require(LEFT_BRACKET, ifStatement);
        List<Statement> statement = new LinkedList<>();
        while (!tokens.matchAndConsume(RIGHT_BRACKET, EOF)) {
            Statement statement1 = parseStatement();
            statement.add(statement1);
        }
        ifStatement.setTrueStatements(statement);
        if(tokens.matchAndConsume(ELSE)){
            require(LEFT_PAREN, ifStatement);
            if(tokens.matchAndConsume(IF)){
                parseIfStatement();
            }
            else{
                require(LEFT_BRACKET, ifStatement);
                List<Statement> stment = new LinkedList<>();
                while (!tokens.matchAndConsume(RIGHT_BRACKET, EOF)) {
                    Statement statement2 = parseStatement();
                    stment.add(statement2);
                }
                ifStatement.setElseStatements(stment);
            }
        }
        ifStatement.setStart(start);
        ifStatement.setEnd(tokens.getCurrentToken());
        return ifStatement;
    }

    private Statement parseForStatement() {
        ForStatement forStatement = new ForStatement();
        Token start = tokens.consumeToken();
        require(LEFT_PAREN, forStatement);
        Token loopIdentifier = require(IDENTIFIER, forStatement);
        String variablename = loopIdentifier.getStringValue();
        require(STRING, forStatement);
        Expression expression = parseExpression();
        if(tokens.matchAndConsume(LEFT_BRACKET)) {
            List<Statement> statement = new LinkedList<>();
            while (!tokens.match(RIGHT_BRACKET, EOF)) {
                Statement statement1 = parseStatement();
                statement.add(statement1);
            }
            forStatement.setBody(statement);
        }
        Token end = tokens.consumeToken();
        forStatement.setStart(start);
        forStatement.setEnd(end);
        forStatement.setVariableName(variablename);
        forStatement.setExpression(expression);
        return forStatement;
    }

    private Statement parsePrintStatement() {
        if (tokens.match(PRINT)) {

            PrintStatement printStatement = new PrintStatement();
            printStatement.setStart(tokens.consumeToken());

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
        return parseEqualityExpression();
    }

    private Expression parseEqualityExpression() {
        Expression expression = parseComparisonExpression();
        while (tokens.match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseComparisonExpression();
            EqualityExpression equalityExpression = new EqualityExpression(operator, expression, rightHandSide);
            equalityExpression.setStart(expression.getStart());
            equalityExpression.setEnd(rightHandSide.getEnd());
            expression = equalityExpression;
        }
        return expression;
    }

    private Expression parseComparisonExpression() {
        Expression expression = parseAdditiveExpression();
        while (tokens.match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseAdditiveExpression();
            ComparisonExpression comparisonExpression = new ComparisonExpression(operator, expression, rightHandSide);
            comparisonExpression.setStart(expression.getStart());
            comparisonExpression.setEnd(rightHandSide.getEnd());
            expression = comparisonExpression;
        }
        return expression;
    }

    private Expression parseAdditiveExpression() {
        Expression expression = parseFactorExpression();
        while (tokens.match(PLUS, MINUS)) {
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseFactorExpression();
            AdditiveExpression additiveExpression = new AdditiveExpression(operator, expression, rightHandSide);
            additiveExpression.setStart(expression.getStart());
            additiveExpression.setEnd(rightHandSide.getEnd());
            expression = additiveExpression;
        }
        return expression;
    }

    private Expression parseFactorExpression() {
        Expression expression = parseUnaryExpression();
        while (tokens.match(SLASH, STAR)) {
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseUnaryExpression();
            FactorExpression factorExpression = new FactorExpression(operator, expression, rightHandSide);
            factorExpression.setStart(expression.getStart());
            factorExpression.setEnd(rightHandSide.getEnd());
            expression = factorExpression;
        }
        return expression;
    }

    private Expression parseUnaryExpression() {
        if (tokens.match(MINUS, NOT)) {
            Token token = tokens.consumeToken();
            Expression rhs = parseUnaryExpression();
            UnaryExpression unaryExpression = new UnaryExpression(token, rhs);
            unaryExpression.setStart(token);
            unaryExpression.setEnd(rhs.getEnd());
            return unaryExpression;
        } else {
            return parsePrimaryExpression();
        }
    }

    private Expression parsePrimaryExpression() {
        if (tokens.match(INTEGER)) {
            Token integerToken = tokens.consumeToken();
            IntegerLiteralExpression integerExpression = new IntegerLiteralExpression(integerToken.getStringValue());
            integerExpression.setToken(integerToken);
            return integerExpression;
        } else if (tokens.match(STRING)) {
            Token stringToken = tokens.consumeToken();
            StringLiteralExpression stringExpression = new StringLiteralExpression(stringToken.getStringValue());
            stringExpression.setToken(stringToken);
            return stringExpression;
        } else if (tokens.match(TRUE) || tokens.match(FALSE)) {
            Token booleanToken = tokens.consumeToken();
            BooleanLiteralExpression booleanLiteralExpression = new BooleanLiteralExpression(booleanToken.getType() == TRUE);
            booleanLiteralExpression.setToken(booleanToken);
            return booleanLiteralExpression;
        } else if (tokens.match(NULL)) {
            Token nullToken = tokens.consumeToken();
            NullLiteralExpression nullLiteralExpression = new NullLiteralExpression();
            nullLiteralExpression.setToken(nullToken);
            return nullLiteralExpression;
        }else if(tokens.match(LEFT_PAREN)){
            Token left = tokens.consumeToken();
            ParenthesizedExpression parenthesizedExpression = new ParenthesizedExpression(parseExpression());
            parenthesizedExpression.setStart(left);
            parenthesizedExpression.setEnd(tokens.getCurrentToken());
            return parenthesizedExpression;
        } else if (tokens.match(IDENTIFIER)){
            Token identifierToken = tokens.consumeToken();
            if(tokens.matchAndConsume(LEFT_PAREN)) {
                List<Expression> expression = new LinkedList<>();
                while(!tokens.match(RIGHT_PAREN, EOF)){
                    Expression expression1 = parseExpression();
                    expression.add(expression1);
                    if(tokens.match(COMMA)){
                        tokens.consumeToken();
                    }
                    else{
                        break;
                    }
                }
                FunctionCallExpression functionCallExpression = new FunctionCallExpression(identifierToken.getStringValue(), expression);
                functionCallExpression.setStart(identifierToken);
                functionCallExpression.setEnd(require(RIGHT_PAREN, functionCallExpression, ErrorType.UNTERMINATED_ARG_LIST));
                /*functionCallExpression.setEnd(tokens.consumeToken());*/

                return functionCallExpression;
            } else{
                    IdentifierExpression identifierExpression = new IdentifierExpression(identifierToken.getStringValue());
                    identifierExpression.setToken(identifierToken);
                    return identifierExpression;}

        } else if (tokens.match(LEFT_BRACKET)) {
            List<Expression> litlit = new LinkedList<>();
            Token lit = tokens.consumeToken();
            if(!tokens.match(RIGHT_BRACKET)){
                litlit.add(parseExpression());
            }
            while (tokens.match(COMMA)) {
                tokens.consumeToken();
                litlit.add(parseExpression());
            }

            ListLiteralExpression listlit = new ListLiteralExpression(litlit);
            if (!tokens.match(RIGHT_BRACKET)) {
                listlit.addError(ErrorType.UNTERMINATED_LIST);
            }
            listlit.setToken(lit);
            return listlit;
        } else {
            SyntaxErrorExpression syntaxErrorExpression = new SyntaxErrorExpression(tokens.consumeToken());
            return syntaxErrorExpression;
        }
    }


    //============================================================
    //  Parse Helpers
    //============================================================
    private Token require(TokenType type, ParseElement elt) {
        return require(type, elt, ErrorType.UNEXPECTED_TOKEN);
    }

    private Token require(TokenType type, ParseElement elt, ErrorType msg) {
        if(tokens.match(type)){
            return tokens.consumeToken();
        } else {
            elt.addError(msg, tokens.getCurrentToken());
            return tokens.getCurrentToken();
        }
    }

}
