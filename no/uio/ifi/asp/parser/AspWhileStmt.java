package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt {
    AspExpr aspExpr;
    AspSuite aspSuite;

    AspWhileStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspWhileStmt parse(Scanner scanner) {
        enterParser("while stmt");
        AspWhileStmt aspWhileStmt = new AspWhileStmt(scanner.curLineNum());

        skip(scanner, whileToken);
        aspWhileStmt.aspExpr = AspExpr.parse(scanner);
        skip(scanner, colonToken);
        aspWhileStmt.aspSuite = AspSuite.parse(scanner);

        leaveParser("while stmt");
        return aspWhileStmt;
    }

    @Override
    void prettyPrint() {
        prettyWrite("while ");
        aspExpr.prettyPrint();
        prettyWrite(": ");
        aspSuite.prettyPrint();
    }
}
