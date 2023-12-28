package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt{
    AspExpr aspExpr;

    public AspExprStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspExprStmt parse(Scanner scanner) {
        enterParser("expr stmt");
        AspExprStmt aspExprStmt = new AspExprStmt(scanner.curLineNum());

        aspExprStmt.aspExpr = AspExpr.parse(scanner);

        leaveParser("expr stmt");
        return aspExprStmt;
    }

    @Override
    public void prettyPrint() {
        aspExpr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspExpr.eval(curScope);
        trace("Expression statement produced " + runtimeValue.toString());
        return runtimeValue;
    }
}
