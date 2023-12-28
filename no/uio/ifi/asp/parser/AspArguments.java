package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> aspExprList = new ArrayList<>();

    AspArguments(int lineNumber) {
        super(lineNumber);
    }

    public static AspArguments parse(Scanner scanner) {
        enterParser("arguments");
        AspArguments aspArguments = new AspArguments(scanner.curLineNum());
    
        skip(scanner, leftParToken);
    
        if (scanner.curToken().kind != rightParToken) {
            aspArguments.aspExprList.add(AspExpr.parse(scanner));
    
            while (scanner.curToken().kind == commaToken) {
                skip(scanner, commaToken);
                aspArguments.aspExprList.add(AspExpr.parse(scanner));
            }
        }
    
        skip(scanner, rightParToken);
    
        leaveParser("arguments");
        return aspArguments;
    }
    
    @Override
    public void prettyPrint() {
        prettyWrite("(");

        boolean isFirst = true;

        for (AspExpr ae : aspExprList) {
            if (!isFirst) {
                prettyWrite(", ");
            } else {
                isFirst = false;
            }

            ae.prettyPrint();
        }

        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> runtimeValueList = new ArrayList<>();

        for (AspExpr aspExpr : aspExprList) {
            runtimeValueList.add(aspExpr.eval(curScope));
        }

        return new RuntimeListValue(runtimeValueList);
    }
}
