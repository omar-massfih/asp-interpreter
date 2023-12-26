package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax {
    AspAtom aspAtom;
    ArrayList<AspPrimarySuffix> aspPrimarySuffixList = new ArrayList<>();

    AspPrimary(int lineNumber) {
        super(lineNumber);
    }

    public static AspPrimary parse(Scanner scanner) {
        enterParser("primary");
        AspPrimary aspPrimary = new AspPrimary(scanner.curLineNum());

        aspPrimary.aspAtom = AspAtom.parse(scanner);

        while (true) {
            if (scanner.curToken().kind == leftParToken || scanner.curToken().kind == leftBracketToken) {
                aspPrimary.aspPrimarySuffixList.add(AspPrimarySuffix.parse(scanner));
            } else {
                break;
            }
        }

        leaveParser("primary");
        return aspPrimary;
    }

    @Override
    void prettyPrint() {
        aspAtom.prettyPrint();

        for (AspPrimarySuffix aspPrimarySuffix : aspPrimarySuffixList) {
            aspPrimarySuffix.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
}
