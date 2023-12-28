package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeFunc;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt {
    public AspName aspName;
    ArrayList<AspName> parameters = new ArrayList<>();
    AspSuite aspSuite;

    AspFuncDef(int lineNumber) {
        super(lineNumber);
    }

    public static AspFuncDef parse(Scanner scanner) {
        enterParser("func def");
        AspFuncDef aspFuncDef = new AspFuncDef(scanner.curLineNum());

        skip(scanner, defToken);
        aspFuncDef.aspName = AspName.parse(scanner);
        skip(scanner, leftParToken);

        if (scanner.curToken().kind == nameToken) {
            while (true) {
                aspFuncDef.parameters.add(AspName.parse(scanner));

                if (scanner.curToken().kind == commaToken) {
                    skip(scanner, commaToken);
                } else {
                    break;
                }
            }
        }

        skip(scanner, rightParToken);
        skip(scanner, colonToken);
        aspFuncDef.aspSuite = AspSuite.parse(scanner);

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

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("def " + aspName.name);
        
        ArrayList<RuntimeValue> runtimeValueList = new ArrayList<>(parameters.size());
        RuntimeStringValue runtimeStringValue = new RuntimeStringValue(aspName.name);

        for (AspName aspName : parameters) {
            runtimeValueList.add(new RuntimeStringValue(aspName.name));
        }

        RuntimeFunc runtimeFunc = new RuntimeFunc(runtimeStringValue, curScope, this, runtimeValueList);
        curScope.assign(aspName.name, runtimeFunc);

        return null;
    }

    public RuntimeValue runFunction(RuntimeScope runtimeScope) throws RuntimeReturnValue {
        return aspSuite.eval(runtimeScope);
    }
}
