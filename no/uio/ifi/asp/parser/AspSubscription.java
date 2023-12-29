package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspSubscription extends AspPrimarySuffix{
    AspExpr aspExpr;

    AspSubscription(int lineNumber) {
        super(lineNumber);
    }

    public static AspSubscription parse(Scanner scanner) {
        enterParser("subscription");

        AspSubscription aspSubscription = new AspSubscription(scanner.curLineNum());
        skip(scanner, leftBracketToken);
        aspSubscription.aspExpr = AspExpr.parse(scanner);
        skip(scanner, rightBracketToken);

        leaveParser("subscription");
        return aspSubscription;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        aspExpr.prettyPrint();
        prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return aspExpr.eval(curScope);
    }
}
