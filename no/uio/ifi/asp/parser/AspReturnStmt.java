package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.returnToken;

public class AspReturnStmt extends AspSmallStmt{
    AspExpr aspExpr;

    AspReturnStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");
        AspReturnStmt aspReturnStmt = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        aspReturnStmt.aspExpr = AspExpr.parse(s);

        leaveParser("return stmt");
        return aspReturnStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return ");
        aspExpr.prettyPrint();
    }
}
