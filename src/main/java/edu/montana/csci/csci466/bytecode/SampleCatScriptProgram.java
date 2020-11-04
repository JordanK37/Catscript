package edu.montana.csci.csci466.bytecode;

import edu.montana.csci.csci466.parser.statements.CatScriptProgram;

public class SampleCatScriptProgram extends CatScriptProgram {
    @Override
    public void execute() {
        int i = 1;
        int j = 1;
        print(i + j);
    }
}
