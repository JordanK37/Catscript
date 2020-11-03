package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.parser.expressions.AdditiveExpression;
import edu.montana.csci.csci466.parser.expressions.IntegerLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.StringLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.SyntaxErrorExpression;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;

public interface ParseTreeVisitor {

    // statements
    void visit(CatScriptProgram catScriptProgram);

    // expressions
    void visit(AdditiveExpression additiveExpression);
    void visit(StringLiteralExpression additiveExpression);
    void visit(IntegerLiteralExpression integerLiteralExpression);

    void visit(SyntaxErrorExpression syntaxErrorExpression);

}
