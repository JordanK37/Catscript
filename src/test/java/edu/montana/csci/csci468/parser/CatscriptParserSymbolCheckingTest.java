package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.CatscriptTestBase;
import edu.montana.csci.csci468.parser.statements.PrintStatement;
import edu.montana.csci.csci468.parser.statements.Statement;
import edu.montana.csci.csci468.parser.statements.VariableStatement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CatscriptParserSymbolCheckingTest extends CatscriptTestBase {

    @Test
    void duplicateVarsErrors() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("var x = 10\n" +
                "var x = 12"));
    }

    @Test
    void varFunctionConflict() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("var x = 10\n" +
                "function x(){}"));
    }

    @Test
    void functionVarConflict() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("var x = 10\n" +
                "function x(){}"));
    }

    @Test
    void functionFunctionConflict() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("function x(){}\n" +
                "function x(){}"));
    }

    @Test
    void forVarConflict() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("var x = 10\n" +
                "for(x in []){ print(x) }"));
    }

    @Test
    void paramsConflictWithGlobalVars() {
        assertEquals(ParseError.DUPLICATE_NAME, getParseError("var x = 10\n" +
                "function foo(x){ print(x) }"));
    }

    @Test
    void paramsDoNotConflictWithEachOther() {
        Statement statement = parseStatement("function foo(x){ print(x) }\n" +
                "function bar(x){ print(x) }\n");
        assertNotNull(statement);
    }

    @Test
    void ifStatementBranchesDoNotConflic() {
        Statement statement = parseStatement("if(true){ var x = 10 } else { var x = true }\n");
        assertNotNull(statement);
    }

}
