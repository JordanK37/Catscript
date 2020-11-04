package edu.montana.csci.csci466.bytecode;

import edu.montana.csci.csci466.parser.statements.CatScriptProgram;

public class SampleCatScriptProgram extends CatScriptProgram {
    int i;
    int j;
    @Override
    public void execute() {
        i=1;
        j=1;
        print(i + j);
    }
}
