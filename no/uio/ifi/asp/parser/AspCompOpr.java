package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspCompOpr extends AspSyntax{
    Token operatorToken;

    AspCompOpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());

        aco.operatorToken = s.curToken();
        skip(s, s.curToken().kind);

        leaveParser("comp opr");
        return aco;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + operatorToken.toString() + " ");
    }
}
