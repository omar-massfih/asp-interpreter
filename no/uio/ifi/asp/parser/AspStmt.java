/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 */

package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspStmt extends AspSyntax {
    AspStmt(int lineNumber) {
        super(lineNumber);
    }

    static AspStmt parse(Scanner scanner) {
        enterParser("stmt");
        
        AspStmt aspStmt = null;
        
        switch (scanner.curToken().kind) {
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
                aspStmt = AspCompoundStmt.parse(scanner);
                break;
            default:
                aspStmt = AspSmallStmtList.parse(scanner);
                break;
        }

        leaveParser("stmt");
        return aspStmt;
    }
}
