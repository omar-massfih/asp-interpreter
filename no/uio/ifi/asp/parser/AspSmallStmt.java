package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmt extends AspSyntax{

    AspSmallStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt aspSmallStmt = null;


        if (s.anyEqualToken()){
            aspSmallStmt = AspAssignmentStmt.parse(s);
        } else if (s.curToken().kind == passToken) {
            aspSmallStmt = AspPassStmt.parse(s);
        } else if (s.curToken().kind == returnToken) {
            aspSmallStmt = AspReturnStmt.parse(s);
        } else if (s.curToken().kind == globalToken) {
            aspSmallStmt = AspGlobalStmt.parse(s);
        } else {
            aspSmallStmt = AspExprStmt.parse(s);
        }
        
        leaveParser("small stmt");
        return aspSmallStmt;
    }
}
