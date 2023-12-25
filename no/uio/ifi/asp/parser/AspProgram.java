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
    ArrayList<AspStmt> statements = new ArrayList<>();

    AspProgram(int lineNumber) {
        super(lineNumber);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram aspProgram = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            aspProgram.statements.add(AspStmt.parse(s));
        }

        leaveParser("program");
        return aspProgram;
    }

    @Override
    public void prettyPrint() {
        for (AspStmt aspStmt : statements) {
            aspStmt.prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
