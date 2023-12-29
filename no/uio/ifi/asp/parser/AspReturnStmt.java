package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.returnToken;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspReturnStmt extends AspSmallStmt{
    AspExpr aspExpr;

    AspReturnStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspReturnStmt parse(Scanner scanner) {
        enterParser("return stmt");

        AspReturnStmt aspReturnStmt = new AspReturnStmt(scanner.curLineNum());
        skip(scanner, returnToken);
        aspReturnStmt.aspExpr = AspExpr.parse(scanner);

        leaveParser("return stmt");
        return aspReturnStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return ");
        aspExpr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspExpr.eval(curScope);
        trace("return " + runtimeValue.showInfo());
        throw new RuntimeReturnValue(runtimeValue, lineNum);
    }
}
