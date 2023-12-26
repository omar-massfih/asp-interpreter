package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt{
    AspExpr aspExpr;

    public AspExprStmt(int n) {
        super(n);
    }

    public static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");
        AspExprStmt aspExprStmt = new AspExprStmt(s.curLineNum());

        aspExprStmt.aspExpr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return aspExprStmt;
    }

    @Override
    public void prettyPrint() {
        aspExpr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }
}
