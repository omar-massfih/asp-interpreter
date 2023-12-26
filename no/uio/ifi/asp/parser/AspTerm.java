package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> aspFactorList = new ArrayList<>();
    ArrayList<AspTermOpr> aspTermOprList = new ArrayList<>();

    AspTerm(int lineNumber) {
        super(lineNumber);
    }

    public static AspTerm parse(Scanner scanner) {
        enterParser("term");
        AspTerm aspTerm = new AspTerm(scanner.curLineNum());

        while (true) {
            aspTerm.aspFactorList.add(AspFactor.parse(scanner));

            if (scanner.isTermOpr()) {
                aspTerm.aspTermOprList.add(AspTermOpr.parse(scanner));
            } else {
                break;
            }
        }

        leaveParser("term");
        return aspTerm;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < aspFactorList.size(); i++) {
            aspFactorList.get(i).prettyPrint();

            if (i < aspTermOprList.size()) {
                aspTermOprList.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
