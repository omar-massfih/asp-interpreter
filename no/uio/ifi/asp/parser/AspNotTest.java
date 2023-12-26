package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspNotTest extends AspSyntax{
    AspComparison aspComparison;
    boolean notTest = false;
    
    AspNotTest(int lineNumber) {
        super(lineNumber);
    }

    public static AspNotTest parse(Scanner s) {
        enterParser("not test");

        AspNotTest aspNotTest = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == notToken) {
            skip(s, notToken);
            aspNotTest.notTest = true;
        }

        aspNotTest.aspComparison = AspComparison.parse(s);

        leaveParser("not test");
        return aspNotTest;
    }

    @Override
    void prettyPrint() {
        if (notTest) {
            prettyWrite("not ");
        }

        aspComparison.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
