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
        if (statement != null) return statement;
        statement = parseFunctionDeclaration();
        if (statement != null) return statement;
        return new SyntaxErrorStatement(tokens.consumeToken());
    }

    private Statement parseFunctionDeclaration() { return null;}
       /* FunctionDefinitionStatement functionDefinitionStatement = new FunctionDefinitionStatement();
        String type = tokens.getCurrentToken().toString();
        if (type.equals("function")){
            Token start = tokens.consumeToken();
            Token name = require(IDENTIFIER, functionDefinitionStatement);
            require(LEFT_PAREN, functionDefinitionStatement);
            List<Expression> expression = new LinkedList<>();
            while(!tokens.matchAndConsume(RIGHT_PAREN)){
                Expression expression1 = parseExpression();
                expression.add(expression1);
            }
            require(COLON, functionDefinitionStatement);
            parseTypeExpression();
            require(LEFT_BRACE, functionDefinitionStatement);
            parsefunctionbodystatement();
            require(RIGHT_BRACE, functionDefinitionStatement);
            functionDefinitionStatement.setStart(start);
            functionDefinitionStatement.getBody();
            functionDefinitionStatement.setEnd(tokens.getCurrentToken());

        }
        return functionDefinitionStatement;
    }
*/
    private Statement parsefunctionbodystatement(){
        Statement statement = parseStatement();
        return statement;
    }


    private Statement parseStatement() {
        Statement printstmt = parsePrintStatement();
        if (printstmt != null) {
            return printstmt;
        }
        if (tokens.match(FOR)) {
            return parseForStatement();
        }
        if (tokens.match(IF)) {
            return parseIfStatement();
        }
        if (tokens.match(VAR)) {
            return parseVarStatement();
        }
        if (tokens.match(IDENTIFIER)) {
            return parseAssignmentStatement();
            }
        if (tokens.match(RETURN)) {
            return parseReturnStatement();
        } else {
            return null;
        }
    }

    /*private Statement parseAssignmentStatement() {
        AssignmentStatement assignmentStatement = new AssignmentStatement();
        Token loop = require(IDENTIFIER, assignmentStatement);
        if (tokens.matchAndConsume(EQUAL)) {
            Token start = tokens.getCurrentToken();
            List<Expression> expression = new LinkedList<>();
            while (!tokens.matchAndConsume(EOF)) {
                Expression expression1 = parseExpression();
                expression.add(expression1);
            }
            assignmentStatement.setStart(start);
            assignmentStatement.setExpression((Expression) expression);
            assignmentStatement.setEnd(tokens.getCurrentToken());
            assignmentStatement.setVariableName(loop.getStringValue());
        }
        return assignmentStatement;
    }*/

    private Statement parseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement();
        if (tokens.match(RETURN)) {
            Token start = tokens.getCurrentToken();
            tokens.consumeToken();
            Expression expression = returnStatement.getExpression();
            returnStatement.setExpression(expression);
            returnStatement.setStart(start);
            returnStatement.setEnd(tokens.getCurrentToken());
        }
        return returnStatement;
    }

    private Statement parseVarStatement() {
        VariableStatement variableStatement = new VariableStatement();
        Token start = tokens.consumeToken();
        Token loopIdentifier = require(IDENTIFIER, variableStatement);
        CatscriptType explicittype = null;
        if (tokens.matchAndConsume(COLON)) {
            TypeLiteral typeLiteral = new TypeLiteral();
            typeLiteral.setType(parseTypeExpression());
            explicittype = typeLiteral.getType();
        }
        if (tokens.matchAndConsume(EQUAL)) {
            Expression expression = parseExpression();
            variableStatement.setExpression(expression);
        }

        variableStatement.setStart(start);
        variableStatement.setExplicitType(explicittype);
        variableStatement.setVariableName(loopIdentifier.getStringValue());
        variableStatement.setEnd(tokens.getCurrentToken());
        return variableStatement;
    }

    private Statement functionCallstatement() {
        tokens.match(IDENTIFIER);
        Token start = tokens.consumeToken();
        tokens.matchAndConsume(LEFT_PAREN);
        List<Expression> expression = new LinkedList<>();
        while(!tokens.match(RIGHT_PAREN)){
            Expression expression1 = parseExpression();
            expression.add(expression1);
        }
        FunctionCallExpression functionCallExpression = new FunctionCallExpression(start.getStringValue(), expression);
        functionCallExpression.setEnd(tokens.getCurrentToken());
        functionCallExpression.setStart(start);
        functionCallExpression.getArguments();
        return null;
    }

    private Statement parseAssignmentStatement() {
        AssignmentStatement assignmentStatement = new AssignmentStatement();
        Token start = require(IDENTIFIER, assignmentStatement);
        require(EQUAL, assignmentStatement);
        Expression expression = parseExpression();
        assignmentStatement.setVariableName(start.getStringValue());
        assignmentStatement.setStart(start);
        assignmentStatement.setExpression(expression);
        assignmentStatement.setEnd(tokens.getCurrentToken());
        return assignmentStatement;
    }



    private CatscriptType parseTypeExpression() {
        if (tokens.match(IDENTIFIER)) {
            String type = tokens.consumeToken().getStringValue();
            if (type.equals("int")) {
                return CatscriptType.INT;
            } else if (type.equals("string")) {
                return CatscriptType.STRING;
            } else if (type.equals("bool")) {
                return CatscriptType.BOOLEAN;
            } else if (type.equals("object")) {
                return CatscriptType.OBJECT;
            } else if (type.equals("list")) {
                if (tokens.matchAndConsume(LESS)) {
                    CatscriptType listType = parseTypeExpression();
                    if (listType != null && tokens.match(GREATER)) {
                        tokens.consumeToken();
                        return new CatscriptType.ListType(CatscriptType.getListType(listType));
                    }
                } else {
                    CatscriptType listType = CatscriptType.OBJECT;
                    return new CatscriptType.ListType(listType);
                }
            }
        }
        return null;
    }


    private Statement parseIfStatement() {
        if (tokens.match(IF)) {
            IfStatement ifStatement = new IfStatement();
            Token start = tokens.consumeToken();
            ifStatement.setStart(start);
            require(LEFT_PAREN, ifStatement);
            List<Expression> expression = new LinkedList<>();
            while (!tokens.matchAndConsume(RIGHT_PAREN)) {
                Expression expression1 = parseExpression();
                expression.add(expression1);
            }
            /*ifStatement.setExpression((Expression) expression);*/
            require(LEFT_BRACE, ifStatement);
            List<Statement> statement = new LinkedList<>();
            while (!tokens.match(RIGHT_BRACE, EOF)) {
                Statement statement1 = parseStatement();
                statement.add(statement1);
            }
            require(RIGHT_BRACE, ifStatement);
            if (tokens.matchAndConsume(ELSE)) {
                require(LEFT_BRACE, ifStatement);
                if (tokens.matchAndConsume(IF)){
                    parseIfStatement();
                } else {
                    while (!tokens.match(RIGHT_BRACE, EOF)) {
                        Statement statement1 = parseStatement();
                        statement.add(statement1);
                    }
                    ifStatement.setTrueStatements(statement);
                    ifStatement.setEnd(require(RIGHT_BRACE, ifStatement));
                }
            } return ifStatement;
        } else {
                return null;
        }
    }



    private Statement parseForStatement() {
        if (tokens.match(FOR)) {
            ForStatement forStatement = new ForStatement();
            forStatement.setStart(tokens.consumeToken());
            require(LEFT_PAREN, forStatement);
            Token loopIdentifier = require(IDENTIFIER, forStatement);

            require(IN, forStatement);
            forStatement.setExpression(parseExpression());
            require(RIGHT_BRACKET, forStatement);
            require(RIGHT_PAREN, forStatement);
            require(LEFT_BRACE, forStatement);
            List<Statement> statement = new ArrayList<>();
            while (!tokens.match(RIGHT_BRACE)) {
                statement.add(parseStatement());
                if(tokens.match(EOF)) {
                    forStatement.addError(ErrorType.UNEXPECTED_TOKEN);
                    break;
                }
            }
            forStatement.setBody(statement);
            if(!tokens.match(RIGHT_BRACE)){
                forStatement.addError(ErrorType.UNEXPECTED_TOKEN);
            }else {
                forStatement.setEnd(require(RIGHT_BRACE, forStatement));
                forStatement.setVariableName(loopIdentifier.getStringValue());
            }
            return forStatement;
        } else {
            return null;
        }
    }

        private Statement parsePrintStatement () {
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
