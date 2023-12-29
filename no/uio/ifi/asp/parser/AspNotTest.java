package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspNotTest extends AspSyntax{
    AspComparison aspComparison;
    boolean notTest;
    
    AspNotTest(int lineNumber) {
        super(lineNumber);
        notTest = false;
    }

    public static AspNotTest parse(Scanner scanner) {
        enterParser("not test");

        AspNotTest aspNotTest = new AspNotTest(scanner.curLineNum());

        if (scanner.curToken().kind == notToken) {
            skip(scanner, notToken);
            aspNotTest.notTest = true;
        }

        aspNotTest.aspComparison = AspComparison.parse(scanner);

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
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspComparison.eval(curScope);
        return notTest ? runtimeValue.evalNot(this) : runtimeValue;
    }
}
