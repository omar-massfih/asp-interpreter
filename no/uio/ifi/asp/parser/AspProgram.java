/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 */

package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> aspStmtList = new ArrayList<>();

    AspProgram(int lineNumber) {
        super(lineNumber);
    }

    public static AspProgram parse(Scanner scanner) {
        enterParser("program");
        AspProgram aspProgram = new AspProgram(scanner.curLineNum());
        
        while (scanner.curToken().kind != eofToken) {
            aspProgram.aspStmtList.add(AspStmt.parse(scanner));
        }

        leaveParser("program");
        return aspProgram;
    }

    @Override
    public void prettyPrint() {
        for (AspStmt aspStmt : aspStmtList) {
            aspStmt.prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = null;

        for (AspStmt aspStmt : aspStmtList) {
            runtimeValue = aspStmt.eval(curScope);
        }

        return runtimeValue;
    }
}
