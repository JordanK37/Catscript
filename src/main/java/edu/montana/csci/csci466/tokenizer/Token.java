package edu.montana.csci.csci466.tokenizer;

public class Token {

    public static final Token EOF_TOKEN = new Token(0,0,0,0, "<EOF>", TokenType.EOF);

    int start;
    int end;
    int line;
    int offset;
    String stringValue;
    TokenType type;

    public Token(int start, int end, int line, int offset, String stringValue, TokenType type) {
        this.start = start;
        this.end = end;
        this.line = line;
        this.offset = offset;
        this.stringValue = stringValue;
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLine() {
        return line;
    }

    public int getOffset() {
        return offset;
    }

    public String getStringValue() {
        return stringValue;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token(\"" + stringValue + "\"){" +
                "type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", line=" + line +
                ", offset=" + offset +
                '}';
    }
}
