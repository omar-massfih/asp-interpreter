package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> aspExprList = new ArrayList<>();
    ArrayList<AspSuite> aspSuiteList = new ArrayList<>();
    AspSuite elseSuite;

    AspIfStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt aspIfStmt = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while (true) {
            aspIfStmt.aspExprList.add(AspExpr.parse(s));
            skip(s, colonToken);
            aspIfStmt.aspSuiteList.add(AspSuite.parse(s));

            if (s.curToken().kind == elseToken) {
                skip(s, elseToken);
                skip(s, colonToken);
                aspIfStmt.elseSuite = AspSuite.parse(s);
                break;
            } else if (s.curToken().kind == elifToken) {
                skip(s, elifToken);
            } else {
                break;
            }
        }

        leaveParser("if stmt");
        return aspIfStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("if ");
        aspExprList.get(0).prettyPrint();
        prettyWrite(": ");
        aspSuiteList.get(0).prettyPrint();

        for (int i = 1; i < aspExprList.size(); i++) {
            prettyWrite("elif ");
            aspExprList.get(i).prettyPrint();
            prettyWrite(": ");
            aspSuiteList.get(i).prettyPrint();
        }

        if (elseSuite != null) {
            prettyWrite("else: ");
            elseSuite.prettyPrint();
        }
    }
}
