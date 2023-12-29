package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.List;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> aspFactorPrefixList = new ArrayList<>();
    ArrayList<AspPrimary> aspPrimaryList = new ArrayList<>();
    ArrayList<AspFactorOpr> aspFactorOprList = new ArrayList<>();

    AspFactor(int lineNumber) {
        super(lineNumber);
    }

    public static AspFactor parse(Scanner scanner) {
        enterParser("factor");
        
        AspFactor aspFactor = new AspFactor(scanner.curLineNum());

        while (true) {
            if (scanner.isFactorPrefix()) {
                aspFactor.aspFactorPrefixList.add(AspFactorPrefix.parse(scanner));
            } else {
                aspFactor.aspFactorPrefixList.add(null);
            }

            aspFactor.aspPrimaryList.add(AspPrimary.parse(scanner));

            if (!scanner.isFactorOpr()) {
                break;
            }

            aspFactor.aspFactorOprList.add(AspFactorOpr.parse(scanner));
        }

        leaveParser("factor");
        return aspFactor;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < aspPrimaryList.size(); i++) {
            if (i < aspFactorPrefixList.size() && aspFactorPrefixList.get(i) != null) {
                aspFactorPrefixList.get(i).prettyPrint();
            }

            aspPrimaryList.get(i).prettyPrint();

            if (i < aspFactorOprList.size()) {
                aspFactorOprList.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeValue = aspPrimaryList.get(0).eval(curScope);

        if (!aspFactorPrefixList.isEmpty() && aspFactorPrefixList.get(0) != null) {
            runtimeValue = evalPrefix(runtimeValue, aspFactorPrefixList, 0);
        }

        for (int i = 1; i < aspPrimaryList.size(); i++) {
            TokenKind tokenKind = aspFactorOprList.get(i - 1).token.kind;
            RuntimeValue runtimeValue2 = aspPrimaryList.get(i).eval(curScope);

            runtimeValue2 = evalPrefix(runtimeValue2, aspFactorPrefixList, i);

            switch (tokenKind) {
                case astToken:
                    runtimeValue = runtimeValue.evalMultiply(runtimeValue2, this);
                    break;
                case slashToken:
                    runtimeValue = runtimeValue.evalDivide(runtimeValue2, this);
                    break;
                case doubleSlashToken:
                    runtimeValue = runtimeValue.evalIntDivide(runtimeValue2, this);
                    break;
                case percentToken:
                    runtimeValue = runtimeValue.evalModulo(runtimeValue2, this);
                    break;
                default:
                    parserError("Factor operator not allowed " + aspFactorOprList.get(i).token, i);
            }
        }

        return runtimeValue;
    }

    private RuntimeValue evalPrefix(RuntimeValue returnValue, List<AspFactorPrefix> aspFactorPrefixList, int index) {
        if (aspFactorPrefixList.get(index) != null) {
            TokenKind tokenKind = aspFactorPrefixList.get(index).factorKind;
            switch (tokenKind) {
                case plusToken:
                    return returnValue.evalPositive(this);
                case minusToken:
                    return returnValue.evalNegate(this);
                default:
                    parserError("Prefix operator not allowed" + aspFactorPrefixList.get(index).factorKind, index);
            }
        }

        return returnValue;
    }
}
