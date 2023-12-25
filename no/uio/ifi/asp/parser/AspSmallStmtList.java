package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;
import static no.uio.ifi.asp.scanner.TokenKind.semicolonToken;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSmallStmtList extends AspStmt{
    ArrayList<AspSmallStmt> aspSmallStatetments = new ArrayList<>();

    AspSmallStmtList(int lineNumber) {
        super(lineNumber);
    }

    public static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList aspSmallStmtList = new AspSmallStmtList(s.curLineNum());
        aspSmallStmtList.aspSmallStatetments.add(AspSmallStmt.parse(s));
        
        while (s.curToken().kind == semicolonToken) {
            skip(s, semicolonToken);

            if (s.curToken().kind == newLineToken) {
                break;
            }

            aspSmallStmtList.aspSmallStatetments.add(AspSmallStmt.parse(s));
        }

        skip(s, newLineToken);
        leaveParser("small stmt list");
        return aspSmallStmtList;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < aspSmallStatetments.size(); i++) {
            aspSmallStatetments.get(i).prettyPrint();

            if (i < aspSmallStatetments.size() - 1) {
                prettyWrite("; ");
            }
        }
        
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

}
