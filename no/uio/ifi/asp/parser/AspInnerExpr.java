package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspInnerExpr extends AspAtom {
    AspExpr aspExpr;

    AspInnerExpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspInnerExpr parse(Scanner scanner) {
        enterParser("inner expr");

        AspInnerExpr aspInnerExpr = new AspInnerExpr(scanner.curLineNum());
        skip(scanner, leftParToken);
        aspInnerExpr.aspExpr = AspExpr.parse(scanner);
        skip(scanner, rightParToken);

        leaveParser("inner expr");
        return aspInnerExpr;
    }

    @Override
    void prettyPrint() {
        prettyWrite("(");
        aspExpr.prettyPrint();
        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return aspExpr.eval(curScope);
    }
}
