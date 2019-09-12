/**
 * Mengenang penderitaan saya dalam mengerjakan hal gila
 * Tapi tidak sesuai spec....
 */


package com.example.calculatingwombat.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

@Deprecated
public class LexicographicParser {
    private StreamTokenizer st;

    private static final int INVALID = -1;
    private static final int NONE = 0;
    private static final int PLUS = 1;
    private static final int MINUS = 2;
    private static final int MULTIPLY = 3;
    private static final int DIVIDE = 4;
    private static final int POWER = 10;
    private static final int CONSTANT = 5;
    private static final int LEFT = 7;
    private static final int RIGHT = 8;
    static final int EOF = 9;
    static final int EOL = 12;

    private final DoubleHashMap<String, Integer> mnemonics = new DoubleHashMap<String, Integer>() {{
        put("+", PLUS);
        put("-", MINUS);
        put("*", MULTIPLY);
        put("/", DIVIDE);
        put("^", POWER);
        put("(", LEFT);
        put(")", RIGHT);
    }};

    private static final String CONST = "[0-9]*\\.?[0-9]*";

    LexicographicParser(InputStream stream) {
        Reader r = new BufferedReader(new InputStreamReader(stream));
        st = new StreamTokenizer(r);

        st.resetSyntax();
        st.eolIsSignificant(true);
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.wordChars('0', '9');
        st.wordChars('.', '.');
        st.whitespaceChars('\u0000', ' '); //characters from 0 to 32
        st.whitespaceChars('\n', '\t');
    }

    public String getString() {
        return this.st.sval;
    }

    int nextSymbol() {
        int symbol;

        try {
            switch (st.nextToken()) {
                case StreamTokenizer.TT_EOL:
                    symbol = EOL; break;
                case StreamTokenizer.TT_EOF:
                    symbol = EOF; break;
                case StreamTokenizer.TT_WORD: {
                    if (st.sval.matches(CONST)) symbol = CONSTANT;
                    else symbol = INVALID;
                    break;
                }
                default:
                    symbol = (mnemonics.get(Character.toString((char)st.ttype)) != null) ? mnemonics.get(Character.toString((char)st.ttype)) : NONE; break;
            }
        } catch (IOException e) {
            symbol = EOF;
        }

        return symbol;
    }

    String stringify(int token) {
        return token == CONSTANT ? getString() : mnemonics.getValue(token);
    }

    static boolean isNumber(String str) {
        return str.matches(CONST);
    }
}


