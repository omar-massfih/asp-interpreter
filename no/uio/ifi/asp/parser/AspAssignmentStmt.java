package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.equalToken;

public class AspAssignmentStmt extends AspSmallStmt {
    AspName an;
    ArrayList<AspSubscription> as = new ArrayList<>();
    AspExpr ae;

    AspAssignmentStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspAssignmentStmt parse(Scanner s) {
        enterParser("assignment");
        AspAssignmentStmt aas = new AspAssignmentStmt(s.curLineNum());
        aas.an = AspName.parse(s);

        while (s.curToken().kind != equalToken) {
            aas.as.add(AspSubscription.parse(s));
        }

        skip(s, equalToken);
        aas.ae = AspExpr.parse(s);

        leaveParser("assignment");
        return aas;
    }

    @Override
    public void prettyPrint() {
        an.prettyPrint();

        for (AspSubscription aspSubscription : as) {
            aspSubscription.prettyPrint();
        }

        prettyWrite(" = ");
        ae.prettyPrint();
    }
}
