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

    public static AspFactorPrefix parse(Scanner scanner) {
        enterParser("factor prefix");
        
        AspFactorPrefix aspFactorPrefix = new AspFactorPrefix(scanner.curLineNum());
        aspFactorPrefix.factorKind = scanner.curToken();
        skip(scanner, scanner.curToken().kind);

        leaveParser("factor prefix");
        return aspFactorPrefix;
    }

    @Override
    void prettyPrint() {
        prettyWrite(factorKind.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
