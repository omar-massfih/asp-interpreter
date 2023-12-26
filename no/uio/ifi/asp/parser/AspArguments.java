package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> aspExprs = new ArrayList<>();

    AspArguments(int lineNumber) {
        super(lineNumber);
    }

    public static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments aspArguments = new AspArguments(s.curLineNum());
    
        skip(s, leftParToken);
    
        if (s.curToken().kind != rightParToken) {
            aspArguments.aspExprs.add(AspExpr.parse(s));
    
            while (s.curToken().kind == commaToken) {
                skip(s, commaToken);
                aspArguments.aspExprs.add(AspExpr.parse(s));
            }
        }
    
        skip(s, rightParToken);
    
        leaveParser("arguments");
        return aspArguments;
    }
    
    @Override
    public void prettyPrint() {
        prettyWrite("(");

        boolean isFirst = true;

        for (AspExpr ae : aspExprs) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
