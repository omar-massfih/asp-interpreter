package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.equalToken;

public class AspAssignmentStmt extends AspSmallStmt {
    AspName aspName;
    ArrayList<AspSubscription> aspSubsriptionList = new ArrayList<>();
    AspExpr aspExpr;

    AspAssignmentStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspAssignmentStmt parse(Scanner scanner) {
        enterParser("assignment");
        AspAssignmentStmt aspAssignmentStmt = new AspAssignmentStmt(scanner.curLineNum());
        aspAssignmentStmt.aspName = AspName.parse(scanner);

        while (scanner.curToken().kind != equalToken) {
            aspAssignmentStmt.aspSubsriptionList.add(AspSubscription.parse(scanner));
        }

        skip(scanner, equalToken);
        aspAssignmentStmt.aspExpr = AspExpr.parse(scanner);
        leaveParser("assignment");
        return aspAssignmentStmt;
    }

    @Override
    public void prettyPrint() {
        aspName.prettyPrint();

        for (AspSubscription aspSubscription : aspSubsriptionList) {
            aspSubscription.prettyPrint();
        }

        prettyWrite(" = ");
        aspExpr.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspExpr.eval(curScope);
        
        if (aspSubsriptionList.isEmpty()) {
            curScope.assign(aspName.getName(), runtimeValue);
            trace(aspName.getName() + " = " + runtimeValue.toString());
        } else {
            RuntimeValue runtimeValue2 = aspName.eval(curScope);
    
            for (int i = 0; i < aspSubsriptionList.size() - 1; i++) {
                RuntimeValue index = aspSubsriptionList.get(i).eval(curScope); 
                runtimeValue2 = runtimeValue2.evalSubscription(index, this);
            }
            
            RuntimeValue inx = aspSubsriptionList.get(aspSubsriptionList.size() - 1).eval(curScope);
            trace(aspName.getName() + "[" + inx.toString() + "] = " + runtimeValue.toString());
            runtimeValue2.evalAssignElem(inx, runtimeValue, this);
        }

        return null;
    }
}
