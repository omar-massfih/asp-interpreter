package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspTermOpr extends AspSyntax {
    Token token;

    AspTermOpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspTermOpr parse(Scanner scanner) {
        enterParser("term opr");

        AspTermOpr aspTermOpr = new AspTermOpr(scanner.curLineNum());
        aspTermOpr.token = scanner.curToken();
        skip(scanner, scanner.curToken().kind);
        
        leaveParser("term opr");
        return aspTermOpr;
    }

    @Override
    void prettyPrint() {
        prettyWrite(" " + token.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
