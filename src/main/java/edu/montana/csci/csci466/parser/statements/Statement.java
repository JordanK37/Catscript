package edu.montana.csci.csci466.parser.statements;

import edu.montana.csci.csci466.parser.ParseElement;

public abstract class Statement extends ParseElement {
    abstract void execute();
}
