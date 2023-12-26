package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspInnerExpr extends AspAtom {
    AspExpr aspExpr;

    AspInnerExpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aspInnerExpr = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        aspInnerExpr.aspExpr = AspExpr.parse(s);
        skip(s, rightParToken);

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
