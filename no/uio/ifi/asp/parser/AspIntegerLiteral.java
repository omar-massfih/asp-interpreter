package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeIntegerValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspIntegerLiteral extends AspAtom {
    long integerLiteral;

    AspIntegerLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspIntegerLiteral parse(Scanner scanner) {
        enterParser("integer literal");

        AspIntegerLiteral aspIntegerLiteral = new AspIntegerLiteral(scanner.curLineNum());
        aspIntegerLiteral.integerLiteral = scanner.curToken().integerLit;
        skip(scanner, integerToken);

        leaveParser("integer literal");
        return aspIntegerLiteral;
    }

    @Override
    void prettyPrint() {
        prettyWrite("" + integerLiteral);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeIntegerValue(integerLiteral);
    }
}
