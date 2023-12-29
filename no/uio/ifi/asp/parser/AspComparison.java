package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
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
        aspComparison.aspTermList.add(AspTerm.parse(scanner));

        while (scanner.isCompOpr()) {
            aspComparison.aspCompOprList.add(AspCompOpr.parse(scanner));
            aspComparison.aspTermList.add(AspTerm.parse(scanner));
        }

        leaveParser("comparison");
        return aspComparison;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < aspTermList.size(); i++) {
            aspTermList.get(i).prettyPrint();
            
            if (i < aspCompOprList.size()) {
                aspCompOprList.get(i).prettyPrint();
            }
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
                TokenKind tokenKind = aspCompOprList.get(i - 1).operatorToken.kind;

                switch (tokenKind) {
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
                        Main.panic("Comparison operator: " + tokenKind + " not allowed.");
                        break;
                }
            }

            if (!returnValue.getBoolValue("comparison operand", this)) {
                return returnValue;
            }
        }

        return returnValue;
    }
}
