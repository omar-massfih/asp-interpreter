package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import java.util.ArrayList;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> aspExprList = new ArrayList<>();
    ArrayList<AspSuite> aspSuiteList = new ArrayList<>();
    AspSuite elseSuite;

    AspIfStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspIfStmt parse(Scanner scanner) {
        enterParser("if stmt");

        AspIfStmt aspIfStmt = new AspIfStmt(scanner.curLineNum());
        skip(scanner, ifToken);

        while (true) {
            aspIfStmt.aspExprList.add(AspExpr.parse(scanner));
            skip(scanner, colonToken);
            aspIfStmt.aspSuiteList.add(AspSuite.parse(scanner));

            if (scanner.curToken().kind == elseToken) {
                skip(scanner, elseToken);
                skip(scanner, colonToken);
                aspIfStmt.elseSuite = AspSuite.parse(scanner);
                break;
            } else if (scanner.curToken().kind == elifToken) {
                skip(scanner, elifToken);
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

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (int i = 0; i < aspExprList.size(); ++i) {
            if (aspExprList.get(i).eval(curScope).getBoolValue("if", this)) {
                trace("if True alt #" + (i + 1) + ": ...");
                aspSuiteList.get(i).eval(curScope);
                return null;
            }
        }
        
        if (elseSuite != null) {
            trace("else: ...");
            elseSuite.eval(curScope);
        }
        
        return null;
    }
}
