package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspIntegerLiteral extends AspAtom {
    long integerLiteral;

    AspIntegerLiteral(int lineNumber) {
        super(lineNumber);
    }

    public static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");

        AspIntegerLiteral aspIntegerLiteral = new AspIntegerLiteral(s.curLineNum());
        aspIntegerLiteral.integerLiteral = s.curToken().integerLit;
        skip(s, integerToken);

        leaveParser("integer literal");
        return aspIntegerLiteral;
    }

    @Override
    void prettyPrint() {
        prettyWrite("" + integerLiteral);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
