package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.passToken;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspPassStmt extends AspSmallStmt{

    AspPassStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspPassStmt parse(Scanner scanner) {
        enterParser("pass stmt");

        AspPassStmt aspPassStmt = new AspPassStmt(scanner.curLineNum());
        skip(scanner, passToken);

        leaveParser("pass stmt");
        return aspPassStmt;
    }

    @Override
    public void prettyPrint(){
        prettyWrite("pass");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("pass");
        return null;
    }
}
