package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.scanner.Scanner;
import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt {
    public AspName aspName;
    ArrayList<AspName> parameters = new ArrayList<>();
    AspSuite aspSuite;

    AspFuncDef(int lineNumber) {
        super(lineNumber);
    }

    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef aspFuncDef = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        aspFuncDef.aspName = AspName.parse(s);
        skip(s, leftParToken);

        if (s.curToken().kind == nameToken) {
            while (true) {
                aspFuncDef.parameters.add(AspName.parse(s));

                if (s.curToken().kind == commaToken) {
                    skip(s, commaToken);
                } else {
                    break;
                }
            }
        }

        skip(s, rightParToken);
        skip(s, colonToken);
        aspFuncDef.aspSuite = AspSuite.parse(s);

        leaveParser("func def");
        return aspFuncDef;
    }

    @Override
    void prettyPrint() {
        prettyWrite("def ");
        aspName.prettyPrint();
        prettyWrite(" (");

        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).prettyPrint();

            if (parameters.size() > 1 && i < parameters.size() - 1) {
                prettyWrite(", ");
            }
        }

        prettyWrite(")");
        prettyWrite(": ");
        aspSuite.prettyPrint();
        prettyWriteLn();
    }
}
