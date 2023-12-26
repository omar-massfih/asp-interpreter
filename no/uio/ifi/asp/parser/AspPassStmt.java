package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.passToken;

import no.uio.ifi.asp.scanner.Scanner;

public class AspPassStmt extends AspSmallStmt{

    AspPassStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspPassStmt parse(Scanner s) {
        enterParser("pass stmt");
        AspPassStmt aps = new AspPassStmt(s.curLineNum());

        skip(s, passToken);

        leaveParser("pass stmt");
        return aps;
    }

    @Override
    public void prettyPrint(){
        prettyWrite("pass");
    }
}
