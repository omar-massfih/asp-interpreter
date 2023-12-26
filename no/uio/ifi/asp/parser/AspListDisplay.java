package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> aspExpr = new ArrayList<>();

    AspListDisplay(int lineNumber) {
        super(lineNumber);
    }

    public static AspListDisplay parse(Scanner scanner) {
        enterParser("list display");
        AspListDisplay aspListDisplay = new AspListDisplay(scanner.curLineNum());

        skip(scanner, leftBracketToken);

        while (true) {
            if (scanner.curToken().kind != rightBracketToken) {
                aspListDisplay.aspExpr.add(AspExpr.parse(scanner));
                
                if (scanner.curToken().kind == commaToken) {
                    skip(scanner, commaToken);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        skip(scanner, rightBracketToken);

        leaveParser("list display");
        return aspListDisplay;
    }

    @Override
    void prettyPrint() {
        prettyWrite("[");

        for (int i = 0; i < aspExpr.size(); i++) {
            aspExpr.get(i).prettyPrint();

            if (i < aspExpr.size() - 1) {
                prettyWrite(", ");
            }
        }

        prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
