package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeDictValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> aspStringLiterals = new ArrayList<>();
    ArrayList<AspExpr> aspExprs = new ArrayList<>();

    AspDictDisplay(int lineNumber) {
        super(lineNumber);
    }

    public static AspDictDisplay parse(Scanner scanner) {
        enterParser("dict displat");

        AspDictDisplay aspDictDisplay = new AspDictDisplay(scanner.curLineNum());
        skip(scanner, leftBraceToken);

        while (true) {
            if (scanner.curToken().kind == stringToken) {
                aspDictDisplay.aspStringLiterals.add(AspStringLiteral.parse(scanner));
                skip(scanner, colonToken);
                aspDictDisplay.aspExprs.add(AspExpr.parse(scanner));

                if (scanner.curToken().kind == commaToken) {
                    skip(scanner, commaToken);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        skip(scanner, rightBraceToken);
        leaveParser("dict display");
        return aspDictDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");

        for (int i = 0; i < aspStringLiterals.size(); i++) {
            if (i > 0) {
                prettyWrite(", ");
            }

            aspStringLiterals.get(i).prettyPrint();
            prettyWrite(":");
            aspExprs.get(i).prettyPrint();
        }

        prettyWrite("}");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeDictValue runtimeDictValue = new RuntimeDictValue(new HashMap<>());

        for (int i = 0; i < aspStringLiterals.size(); i++) {
            runtimeDictValue.getDict().put(aspStringLiterals.get(i).stringLiteral , aspExprs.get(i).eval(curScope));
        }

        return runtimeDictValue;
    }
}
