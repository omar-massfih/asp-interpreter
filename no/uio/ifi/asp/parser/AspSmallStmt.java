package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspSmallStmt extends AspSyntax{

    AspSmallStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspSmallStmt parse(Scanner scanner) {
        enterParser("small stmt");
        AspSmallStmt aspSmallStmt = null;


        if (scanner.anyEqualToken()){
            aspSmallStmt = AspAssignmentStmt.parse(scanner);
        } else if (scanner.curToken().kind == passToken) {
            aspSmallStmt = AspPassStmt.parse(scanner);
        } else if (scanner.curToken().kind == returnToken) {
            aspSmallStmt = AspReturnStmt.parse(scanner);
        } else if (scanner.curToken().kind == globalToken) {
            aspSmallStmt = AspGlobalStmt.parse(scanner);
        } else {
            aspSmallStmt = AspExprStmt.parse(scanner);
        }
        
        leaveParser("small stmt");
        return aspSmallStmt;
    }
}
