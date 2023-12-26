package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspFactorPrefix extends AspSyntax {
    Token factorKind;
    
    AspFactorPrefix(int lineNumber) {
        super(lineNumber);
    }

    public static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix aspFactorPrefix = new AspFactorPrefix(s.curLineNum());

        aspFactorPrefix.factorKind = s.curToken();
        skip(s, s.curToken().kind);

        leaveParser("factor prefix");
        return aspFactorPrefix;
    }

    @Override
    void prettyPrint() {
        prettyWrite(factorKind.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
