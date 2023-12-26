package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspBooleanLiteral extends AspAtom{
    String booleanString;
    boolean bool;
    TokenKind booleanKind;

    AspBooleanLiteral(int n) {
        super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral aspBooleanLiteral = new AspBooleanLiteral(s.curLineNum());
        aspBooleanLiteral.booleanString = s.curToken().toString();

        switch (aspBooleanLiteral.booleanString) {
            case "True":
                aspBooleanLiteral.bool = true;
                aspBooleanLiteral.booleanKind = trueToken;
                break;
            default:
                aspBooleanLiteral.bool = false;
                aspBooleanLiteral.booleanKind = falseToken;
                break;
        }

        skip(s, aspBooleanLiteral.booleanKind);
        leaveParser("boolean literal");
        return aspBooleanLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(bool ? "True" : "False");
    }
}
