package edu.montana.csci.csci466.parser.expressions;

import edu.montana.csci.csci466.parser.ParseElement;
import edu.montana.csci.csci466.tokenizer.Token;

public abstract class Expression extends ParseElement {
    public Object evaluate() {
        return null;
    }
}
