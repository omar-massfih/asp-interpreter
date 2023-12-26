package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.leftParToken;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspPrimarySuffix extends AspSyntax {
    AspPrimarySuffix aspPrimarySuffix;

    AspPrimarySuffix(int lineNumber) {
        super(lineNumber);
    }

    public static AspPrimarySuffix parse(Scanner scanner) {
        enterParser("primary suffix");
        AspPrimarySuffix aspPrimarySuffix = null;

        if (scanner.curToken().kind == leftParToken) {
            aspPrimarySuffix = AspArguments.parse(scanner);
        } else {
            aspPrimarySuffix = AspSubscription.parse(scanner);
        }

        leaveParser("primary suffix");
        return aspPrimarySuffix;
    }
}
