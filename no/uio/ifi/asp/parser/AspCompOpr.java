package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspCompOpr extends AspSyntax{
    Token operatorToken;

    AspCompOpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspCompOpr parse(Scanner scanner) {
        enterParser("comp opr");
        AspCompOpr aspCompOpr = new AspCompOpr(scanner.curLineNum());

        aspCompOpr.operatorToken = scanner.curToken();
        skip(scanner, scanner.curToken().kind);

        leaveParser("comp opr");
        return aspCompOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + operatorToken.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
