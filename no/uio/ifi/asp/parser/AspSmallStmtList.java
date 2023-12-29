package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;
import static no.uio.ifi.asp.scanner.TokenKind.semicolonToken;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSmallStmtList extends AspStmt{
    ArrayList<AspSmallStmt> aspSmallStmtList = new ArrayList<>();

    AspSmallStmtList(int lineNumber) {
        super(lineNumber);
    }

    public static AspSmallStmtList parse(Scanner scanner) {
        enterParser("small stmt list");

        AspSmallStmtList aspSmallStmtList = new AspSmallStmtList(scanner.curLineNum());
        aspSmallStmtList.aspSmallStmtList.add(AspSmallStmt.parse(scanner));
        
        while (scanner.curToken().kind == semicolonToken) {
            skip(scanner, semicolonToken);

            if (scanner.curToken().kind == newLineToken) {
                break;
            }

            aspSmallStmtList.aspSmallStmtList.add(AspSmallStmt.parse(scanner));
        }

        skip(scanner, newLineToken);

        leaveParser("small stmt list");
        return aspSmallStmtList;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < aspSmallStmtList.size(); i++) {
            if (i > 0) {
                prettyWrite("; ");
            }
            
            aspSmallStmtList.get(i).prettyPrint();
        }
        
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = null;

        for (AspSmallStmt aspSmallStmt : aspSmallStmtList) {
            runtimeValue = aspSmallStmt.eval(curScope);
        }

        return runtimeValue;
    }
}
