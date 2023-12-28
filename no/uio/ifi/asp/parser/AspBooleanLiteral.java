package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspBooleanLiteral extends AspAtom{
    String booleanString;
    boolean booleanLiteral;
    TokenKind booleanKind;

    AspBooleanLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspBooleanLiteral parse(Scanner scanner) {
        enterParser("boolean literal");
        AspBooleanLiteral aspBooleanLiteral = new AspBooleanLiteral(scanner.curLineNum());
        aspBooleanLiteral.booleanString = scanner.curToken().toString();

        switch (aspBooleanLiteral.booleanString) {
            case "True":
                aspBooleanLiteral.booleanLiteral = true;
                aspBooleanLiteral.booleanKind = trueToken;
                break;
            default:
                aspBooleanLiteral.booleanLiteral = false;
                aspBooleanLiteral.booleanKind = falseToken;
                break;
        }

        skip(scanner, aspBooleanLiteral.booleanKind);
        leaveParser("boolean literal");
        return aspBooleanLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(booleanLiteral ? "True" : "False");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeBoolValue(booleanLiteral);
    }
}
