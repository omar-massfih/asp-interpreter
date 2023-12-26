package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.nameToken;

public class AspName extends AspAtom {
    public String name;

    AspName(int lineNumber) {
        super(lineNumber);
    }

    public static AspName parse(Scanner s) {
        enterParser("name");

        AspName an = new AspName(s.curLineNum());
        an.name = s.curToken().name;
        skip(s, nameToken);

        leaveParser("name");
        return an;
    }

    @Override
    void prettyPrint() {
        prettyWrite(name + "");
    }
}
