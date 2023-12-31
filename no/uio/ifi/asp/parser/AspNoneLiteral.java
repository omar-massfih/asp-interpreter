package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspNoneLiteral extends AspAtom{

    AspNoneLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspNoneLiteral parse(Scanner scanner) {
        enterParser("none literal");

        AspNoneLiteral aspNoneLiteral = new AspNoneLiteral(scanner.curLineNum());
        skip(scanner, noneToken);

        leaveParser("none literal");
        return aspNoneLiteral;
    }

    @Override
    void prettyPrint() {
        prettyWrite("None");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeNoneValue();
    }
}
