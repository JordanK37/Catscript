package edu.montana.csci.csci466.parser.statements;

public interface Statement {
    void setParent(Statement statement);
    void execute();
}
