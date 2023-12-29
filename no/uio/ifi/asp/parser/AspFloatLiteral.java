package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeFloatValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspFloatLiteral extends AspAtom {
    double floatLiteral;

    AspFloatLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspFloatLiteral parse(Scanner scanner) {
        enterParser("float literal");

        AspFloatLiteral aspFloatLiteral = new AspFloatLiteral(scanner.curLineNum());
        aspFloatLiteral.floatLiteral = scanner.curToken().floatLit;
        skip(scanner, floatToken);

        leaveParser("float literal");
        return aspFloatLiteral;
    }

    @Override
    void prettyPrint() {
        prettyWrite(floatLiteral + "");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(floatLiteral);
    }
}
