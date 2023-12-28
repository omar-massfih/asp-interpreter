package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspForStmt extends AspCompoundStmt {
    AspName aspName;
    AspExpr aspExpr;
    AspSuite aspSuite;

    public AspForStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspForStmt parse(Scanner scanner) {
        enterParser("for stmt");
        AspForStmt aspForStmt = new AspForStmt(scanner.curLineNum());

        skip(scanner, forToken);
        aspForStmt.aspName = AspName.parse(scanner);
        skip(scanner, inToken);
        aspForStmt.aspExpr = AspExpr.parse(scanner);
        skip(scanner, colonToken);
        aspForStmt.aspSuite = AspSuite.parse(scanner);

        leaveParser("for stmt");
        return aspForStmt;
    }

    @Override
    void prettyPrint() {
        prettyWrite("for ");
        aspName.prettyPrint();
        prettyWrite(" in ");
        aspExpr.prettyPrint();
        prettyWrite(": ");
        aspSuite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue rv = aspExpr.eval(curScope);
        
        if (rv instanceof RuntimeListValue) {
            ArrayList<RuntimeValue> list = ((RuntimeListValue) rv).getRuntimeValueList();

            int i = 0;

            while (list.get(i) == null) {
                i++;
            }
            
            for (int j = i; j < list.size(); j++) {
                trace("for #" + (j + 1) + ": " + aspName.name + " = " + (j + 1));
                curScope.assign(aspName.name, list.get(j));
                aspSuite.eval(curScope);
            }

        }

        return rv;
    }
}
