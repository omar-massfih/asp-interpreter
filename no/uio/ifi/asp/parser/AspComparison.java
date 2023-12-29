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
        RuntimeValue returnValue = aspTermList.get(0).eval(curScope);
        RuntimeValue res = returnValue;

        for (int i = 1; i < aspTermList.size(); i++) {
            RuntimeValue returnValue2 = aspTermList.get(i).eval(curScope);
            TokenKind tokenKind = aspCompOprList.get(i - 1).operatorToken.kind;

            switch (tokenKind) {
                case lessToken:
                    res = returnValue.evalLess(returnValue2, this);
                    break;
                case greaterToken:
                    res = returnValue.evalGreater(returnValue2, this);
                    break;
                case doubleEqualToken:
                    res = returnValue.evalEqual(returnValue2, this);
                    break;
                case greaterEqualToken:
                    res = returnValue.evalGreaterEqual(returnValue2, this);
                    break;
                case lessEqualToken:
                    res = returnValue.evalLessEqual(returnValue2, this);
                    break;
                case notEqualToken:
                    res = returnValue.evalNotEqual(returnValue2, this);
                    break;
                default:
                    Main.panic("Comparison operator: " + tokenKind + " not allowed.");
                    break;
            }

            if (!returnValue.getBoolValue("comparison operand", this)) {
                return res;
            }

            returnValue = returnValue2;
        }

        return res;
    }
}
