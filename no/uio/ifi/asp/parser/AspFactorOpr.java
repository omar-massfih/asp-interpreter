package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.Token;

public class AspFactorOpr extends AspSyntax{
    Token token;

    AspFactorOpr(int lineNumber) {
        super(lineNumber);
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr aspFactorOpr = new AspFactorOpr(s.curLineNum());

        aspFactorOpr.token = s.curToken();
        skip(s, s.curToken().kind);

        leaveParser("factor opr");
        return aspFactorOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + token.toString() + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
