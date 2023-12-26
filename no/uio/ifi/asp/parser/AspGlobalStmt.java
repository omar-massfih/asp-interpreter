package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt{
    ArrayList<AspName> aspNames = new ArrayList<>();

    public AspGlobalStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt aspGlobalStmt = new AspGlobalStmt(s.curLineNum());

        skip(s, globalToken);

        while (true) {
            aspGlobalStmt.aspNames.add(AspName.parse(s));

            if (s.curToken().kind == commaToken) {
                skip(s, commaToken);
            } else {
                break;
            }
        }

        leaveParser("global stmt");
        return aspGlobalStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("global ");

        for (int i = 0; i < aspNames.size(); i++) {
            aspNames.get(i).prettyPrint();

            if (aspNames.size() > 1 && i < aspNames.size() - 1) {
                prettyWrite(", ");
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }
}
