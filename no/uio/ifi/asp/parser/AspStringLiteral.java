package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspStringLiteral extends AspAtom{
    String stringLiteral;

    AspStringLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspStringLiteral parse(Scanner scanner) {
        enterParser("string literal");

        AspStringLiteral aspStringLiteral = new AspStringLiteral(scanner.curLineNum());
        aspStringLiteral.stringLiteral = scanner.curToken().stringLit;
        skip(scanner, stringToken);

        leaveParser("string literal");
        return aspStringLiteral;
    }

    @Override
    void prettyPrint() {
        if (stringLiteral.contains("\"")) {
            prettyWrite("'" + stringLiteral + "'");
        } else {
            prettyWrite('"' + stringLiteral + '"');
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeStringValue(stringLiteral);
    }
}
