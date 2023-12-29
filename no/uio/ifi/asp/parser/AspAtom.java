package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspAtom extends AspSyntax {

    AspAtom(int lineNumber) {
        super(lineNumber);
    }

    static AspAtom parse(Scanner scanner) {
        enterParser("atom");
        AspAtom aspAtom = null;

        switch (scanner.curToken().kind) {
            case nameToken:
                aspAtom = AspName.parse(scanner);
                break;
            case integerToken:
                aspAtom = AspIntegerLiteral.parse(scanner);
                break;
            case floatToken:
                aspAtom = AspFloatLiteral.parse(scanner);
                break;
            case stringToken:
                aspAtom = AspStringLiteral.parse(scanner);
                break;
            case trueToken:
            case falseToken:
                aspAtom = AspBooleanLiteral.parse(scanner);
                break;
            case noneToken:
                aspAtom = AspNoneLiteral.parse(scanner);
                break;
            case leftParToken:
                aspAtom = AspInnerExpr.parse(scanner);
                break;
            case leftBracketToken:
                aspAtom = AspListDisplay.parse(scanner);
                break;
            case leftBraceToken:
                aspAtom = AspDictDisplay.parse(scanner);
                break;
            default:
                parserError("Expected an expression atom but found a " +
                        scanner.curToken().kind + "!", scanner.curLineNum());
                break;
        }

        leaveParser("atom");
        return aspAtom;
    }
}
