package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspWhileStmt extends AspCompoundStmt {
    AspExpr aspExpr;
    AspSuite aspSuite;

    AspWhileStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspWhileStmt parse(Scanner scanner) {
        enterParser("while stmt");
        AspWhileStmt aspWhileStmt = new AspWhileStmt(scanner.curLineNum());

        skip(scanner, whileToken);
        aspWhileStmt.aspExpr = AspExpr.parse(scanner);
        skip(scanner, colonToken);
        aspWhileStmt.aspSuite = AspSuite.parse(scanner);

        leaveParser("while stmt");
        return aspWhileStmt;
    }

    @Override
    void prettyPrint() {
        prettyWrite("while ");
        aspExpr.prettyPrint();
        prettyWrite(": ");
        aspSuite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = null;
        
        while (true) {
            runtimeValue = aspExpr.eval(curScope);
            
            if (!runtimeValue.getBoolValue("while expr", this)) {
                break;
            }
            
            trace("while True: ...");
            aspSuite.eval(curScope);
        }

        trace("while False:");

        return runtimeValue;
    }
}
