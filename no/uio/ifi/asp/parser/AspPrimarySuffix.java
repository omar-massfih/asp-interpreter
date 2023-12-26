package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.leftParToken;

import no.uio.ifi.asp.scanner.Scanner;

public class AspPrimarySuffix extends AspSyntax {
    AspPrimarySuffix aspPrimarySuffix;

    AspPrimarySuffix(int n) {
        super(n);
    }

    public static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix aps = null;

        if (s.curToken().kind == leftParToken) {
            aps = AspArguments.parse(s);
        } else {
            aps = AspSubscription.parse(s);
        }

        leaveParser("primary suffix");
        return aps;
    }
}
