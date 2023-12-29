package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> aspExprList = new ArrayList<>();

    AspListDisplay(int lineNumber) {
        super(lineNumber);
    }

    public static AspListDisplay parse(Scanner scanner) {
        enterParser("list display");

        AspListDisplay aspListDisplay = new AspListDisplay(scanner.curLineNum());
        skip(scanner, leftBracketToken);

        if (scanner.curToken().kind != rightBracketToken) {
            while (true) {
                aspListDisplay.aspExprList.add(AspExpr.parse(scanner));

                if (scanner.curToken().kind == rightBracketToken) {
                    break;
                }

                skip(scanner, commaToken);
            }
        }

        skip(scanner, rightBracketToken);

        leaveParser("list display");
        return aspListDisplay;
    }

    @Override
    void prettyPrint() {
        prettyWrite("[");

        for (int i = 0; i < aspExprList.size(); i++) {
            if (i > 0) {
                prettyWrite(", ");
            }
            
            aspExprList.get(i).prettyPrint();
        }

        prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeListValue runtimeListValue = new RuntimeListValue(new ArrayList<RuntimeValue>());

        for (AspExpr ae : aspExprList) {
            runtimeListValue.getRuntimeValueList().add(ae.eval(curScope));
        }

        return runtimeListValue;
    }
}
