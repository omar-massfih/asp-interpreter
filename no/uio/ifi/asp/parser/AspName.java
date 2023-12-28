package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.nameToken;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspName extends AspAtom {
    public String name;

    AspName(int lineNumber) {
        super(lineNumber);
    }

    public static AspName parse(Scanner scanner) {
        enterParser("name");

        AspName aspName = new AspName(scanner.curLineNum());
        aspName.name = scanner.curToken().name;
        skip(scanner, nameToken);

        leaveParser("name");
        return aspName;
    }

    @Override
    void prettyPrint() {
        prettyWrite(name + "");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return curScope.find(name, this);
    }
}
