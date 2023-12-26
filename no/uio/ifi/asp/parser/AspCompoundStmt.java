package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspCompoundStmt extends AspStmt {
    AspCompoundStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspCompoundStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspCompoundStmt aspCompundStmt = null;

        switch (s.curToken().kind) {
            case forToken:
                aspCompundStmt = AspForStmt.parse(s);
                break;

            case ifToken:
                aspCompundStmt = AspIfStmt.parse(s);
                break;

            case whileToken:
                aspCompundStmt = AspWhileStmt.parse(s);
                break;

            case defToken:
                aspCompundStmt = AspFuncDef.parse(s);
                break;
            default:
                parserError("Not correct token", s.curLineNum());
                break;
        }

        leaveParser("compound stmt");
        return aspCompundStmt;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

}
