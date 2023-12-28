package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> aspNameList = new ArrayList<>();

    public AspGlobalStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspGlobalStmt parse(Scanner scanner) {
        enterParser("global stmt");
        AspGlobalStmt aspGlobalStmt = new AspGlobalStmt(scanner.curLineNum());

        skip(scanner, globalToken);

        while (true) {
            aspGlobalStmt.aspNameList.add(AspName.parse(scanner));

            if (scanner.curToken().kind == commaToken) {
                skip(scanner, commaToken);
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

        for (int i = 0; i < aspNameList.size(); i++) {
            aspNameList.get(i).prettyPrint();

            if (aspNameList.size() > 1 && i < aspNameList.size() - 1) {
                prettyWrite(", ");
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspName aspName : aspNameList) {
            curScope.registerGlobalName(aspName.name);
        }

        return null;
    }
}
