package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    ArrayList<AspCompOpr> aspCompOprList = new ArrayList<>();
    ArrayList<AspTerm> aspTermList = new ArrayList<>();

    AspComparison(int lineNumber) {
        super(lineNumber);
    }

    public static AspComparison parse(Scanner scanner) {
        enterParser("comparison");
        AspComparison aspComparison = new AspComparison(scanner.curLineNum());

        while (true) {
            aspComparison.aspTermList.add(AspTerm.parse(scanner));

            if (scanner.isCompOpr()) {
                aspComparison.aspCompOprList.add(AspCompOpr.parse(scanner));
            } else {
                break;
            }
        }

        leaveParser("comparison");
        return aspComparison;
    }

    @Override
    public void prettyPrint() {
        int i = 0;

        while (i < aspTermList.size()) {
            aspTermList.get(i).prettyPrint();

            if (i < aspCompOprList.size()) {
                aspCompOprList.get(i).prettyPrint();
            }
            
            i++;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
