package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> aspStringLiterals = new ArrayList<>();
    ArrayList<AspExpr> aspExprs = new ArrayList<>();

    AspDictDisplay(int lineNumber) {
        super(lineNumber);
    }

    public static AspDictDisplay parse(Scanner s) {
        enterParser("dict displat");
        AspDictDisplay aspDictDisplay = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        while (true) {
            if (s.curToken().kind == stringToken) {
                aspDictDisplay.aspStringLiterals.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                aspDictDisplay.aspExprs.add(AspExpr.parse(s));

                if (s.curToken().kind == commaToken) {
                    skip(s, commaToken);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        skip(s, rightBraceToken);

        leaveParser("dict display");
        return aspDictDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");

        for (int i = 0; i < aspStringLiterals.size(); i++) {
            aspStringLiterals.get(i).prettyPrint();
            prettyWrite(":");
            aspExprs.get(i).prettyPrint();

            if (i < aspStringLiterals.size() - 1) {
                prettyWrite(", ");
            }
        }

        prettyWrite("}");
    }
}
