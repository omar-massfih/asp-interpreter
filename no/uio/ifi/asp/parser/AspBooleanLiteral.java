package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspBooleanLiteral extends AspAtom{
    boolean booleanLiteral;

    AspBooleanLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspBooleanLiteral parse(Scanner scanner) {
        enterParser("boolean literal");
        
        AspBooleanLiteral aspBooleanLiteral = new AspBooleanLiteral(scanner.curLineNum());

        switch (scanner.curToken().kind) {
            case trueToken:
                aspBooleanLiteral.booleanLiteral = true;
                skip(scanner, trueToken);
                break;
            default:
                aspBooleanLiteral.booleanLiteral = false;
                skip(scanner,  falseToken);
                break;
        }

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
