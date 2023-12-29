package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> aspNotTestList = new ArrayList<>();

    AspAndTest(int lineNumber) {
        super(lineNumber);
    }

    public static AspAndTest parse(Scanner scanner) {
        enterParser("and test");
        
        AspAndTest aspAndTest = new AspAndTest(scanner.curLineNum());

        while (true) {
            aspAndTest.aspNotTestList.add(AspNotTest.parse(scanner));

            if (scanner.curToken().kind != andToken) {
                break;
            }

            skip(scanner, andToken);
        }

        leaveParser("and test");
        return aspAndTest;
    }

    @Override
    void prettyPrint() {
        aspNotTestList.get(0).prettyPrint();

        for (int i = 1; i < aspNotTestList.size(); i++) {
            prettyWrite(" and ");

            aspNotTestList.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspNotTestList.get(0).eval(curScope);

        for (int i = 1; i < aspNotTestList.size(); i++) {
            if (!runtimeValue.getBoolValue("and operand", this)) {
                return runtimeValue;
            }

            runtimeValue = aspNotTestList.get(i).eval(curScope);
        }

        return runtimeValue;
    }
}
