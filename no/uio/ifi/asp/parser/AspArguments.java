package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

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
            while (true) {
                aspArguments.aspExprList.add(AspExpr.parse(scanner));
                
                if (scanner.curToken().kind == rightParToken) {
                    break;
                }

                skip(scanner, commaToken);
            }
        }
    
        skip(scanner, rightParToken);
        leaveParser("arguments");
        return aspArguments;
    }
    
    @Override
    public void prettyPrint() {
        prettyWrite("(");

        for (int i = 0; i < aspExprList.size(); i++) {
            if (i > 0) {
                prettyWrite(", ");
            }

            aspExprList.get(i).prettyPrint();
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
