package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> aspExprs = new ArrayList<>();

    AspArguments(int lineNumber) {
        super(lineNumber);
    }

    public static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());
    
        skip(s, leftParToken);
    
        if (s.curToken().kind != rightParToken) {
            aa.aspExprs.add(AspExpr.parse(s));
    
            while (s.curToken().kind == commaToken) {
                skip(s, commaToken);
                aa.aspExprs.add(AspExpr.parse(s));
            }
        }
    
        skip(s, rightParToken);
    
        leaveParser("arguments");
        return aa;
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
}
