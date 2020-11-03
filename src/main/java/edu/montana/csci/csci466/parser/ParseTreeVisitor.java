package edu.montana.csci.csci466.parser;

import edu.montana.csci.csci466.parser.expressions.AdditiveExpression;
import edu.montana.csci.csci466.parser.expressions.IntegerLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.StringLiteralExpression;
import edu.montana.csci.csci466.parser.expressions.SyntaxErrorExpression;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.parser.statements.PrintStatement;
import edu.montana.csci.csci466.parser.statements.SyntaxErrorStatement;

public interface ParseTreeVisitor {

    // statements
    void visit(PrintStatement printStatement);
    void visit(CatScriptProgram catScriptProgram);

    void visit(SyntaxErrorStatement syntaxErrorStatement);

    // expressions
    void visit(AdditiveExpression additiveExpression);
    void visit(StringLiteralExpression additiveExpression);
    void visit(IntegerLiteralExpression integerLiteralExpression);

    void visit(SyntaxErrorExpression syntaxErrorExpression);

}
