/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 */

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTestList = new ArrayList<>();

    AspExpr(int lineNum) {
        super(lineNum);
    }

    public static AspExpr parse(Scanner scanner) {
        enterParser("expr");

        AspExpr aspExpr = new AspExpr(scanner.curLineNum());

        while (true) {
            aspExpr.andTestList.add(AspAndTest.parse(scanner));

            if (scanner.curToken().kind != orToken) {
                break;
            }

            skip(scanner, orToken);
        }

        leaveParser("expr");
        return aspExpr;
    }

    @Override
    public void prettyPrint() {
		int n = 0;

        for (AspAndTest aspAndTest : andTestList) {
            if (n > 0) {
                prettyWrite(" or ");
            }

            aspAndTest.prettyPrint();
            n++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 3:
        return null;
    }
}
