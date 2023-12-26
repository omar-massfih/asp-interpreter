package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspSuite extends AspSyntax{
    ArrayList<AspStmt> aspStmtList = new ArrayList<>();
    AspSmallStmtList aspSmallStmtList;

    AspSuite(int lineNumber) {
        super(lineNumber);
    }

    static AspSuite parse(Scanner scanner) {
        enterParser("suite");

        AspSuite aspSuite = new AspSuite(scanner.curLineNum());

        if (scanner.curToken().kind == newLineToken) {
            skip(scanner, newLineToken);
            skip(scanner, indentToken);

            while (scanner.curToken().kind != dedentToken) {
                aspSuite.aspStmtList.add(AspStmt.parse(scanner));
            }

            skip(scanner, dedentToken);
        } else {
            aspSuite.aspSmallStmtList = AspSmallStmtList.parse(scanner);
        }

        leaveParser("suite");
        return aspSuite;
    }

    @Override
    void prettyPrint() {
        if (aspSmallStmtList != null) {
            aspSmallStmtList.prettyPrint();
        } else {
            prettyWriteLn();
            prettyIndent();

            for (AspStmt aspStmt : aspStmtList) {
                aspStmt.prettyPrint();
            }

            prettyDedent();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
