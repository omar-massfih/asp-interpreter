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
            if (i > 0) {
                prettyWrite(", ");
            }

            aspNameList.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspName aspName : aspNameList) {
            if (curScope.hasDefined(aspName.getName())) {
                RuntimeValue.runtimeError("Name " + aspName.getName() + " assigned to before global declaration!", this);
            }

            if (curScope.hasGlobalName(aspName.getName())) {
                RuntimeValue.runtimeError("Name " + aspName.getName() + " already declared as global!", this);
            }

            curScope.registerGlobalName(aspName.getName());
        }

        return null;
    }
}
