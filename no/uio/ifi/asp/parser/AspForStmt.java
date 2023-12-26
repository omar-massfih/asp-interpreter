package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    AspName aspName;
    AspExpr aspExpr;
    AspSuite aspSuite;

    public AspForStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspForStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt aspForStmt = new AspForStmt(s.curLineNum());

        skip(s, forToken);
        aspForStmt.aspName = AspName.parse(s);
        skip(s, inToken);
        aspForStmt.aspExpr = AspExpr.parse(s);
        skip(s, colonToken);
        aspForStmt.aspSuite = AspSuite.parse(s);

        leaveParser("for stmt");
        return aspForStmt;
    }

    @Override
    void prettyPrint() {
        prettyWrite("for ");
        aspName.prettyPrint();
        prettyWrite(" in ");
        aspExpr.prettyPrint();
        prettyWrite(": ");
        aspSuite.prettyPrint();
    }
}
