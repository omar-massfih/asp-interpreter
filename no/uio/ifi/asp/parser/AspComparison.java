package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

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
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (aspTermList.isEmpty()) {
            return null;
        }

        RuntimeValue returnValue = aspTermList.get(0).eval(curScope);

        for (int i = 1; i < aspTermList.size(); i++) {
            returnValue = aspTermList.get(i - 1).eval(curScope);
            RuntimeValue curTerm = aspTermList.get(i).eval(curScope);

            if (i - 1 < aspCompOprList.size()) {
                TokenKind t = aspCompOprList.get(i - 1).operatorToken.kind;

                switch (t) {
                    case lessToken:
                        returnValue = returnValue.evalLess(curTerm, this);
                        break;
                    case greaterToken:
                        returnValue = returnValue.evalGreater(curTerm, this);
                        break;
                    case doubleEqualToken:
                        returnValue = returnValue.evalEqual(curTerm, this);
                        break;
                    case greaterEqualToken:
                        returnValue = returnValue.evalGreaterEqual(curTerm, this);
                        break;
                    case lessEqualToken:
                        returnValue = returnValue.evalLessEqual(curTerm, this);
                        break;
                    case notEqualToken:
                        returnValue = returnValue.evalNotEqual(curTerm, this);
                        break;
                    default:
                        break;
                }
            }

            if (!returnValue.getBoolValue("", this)) {
                return returnValue;
            }
        }

        return returnValue;
    }
}
