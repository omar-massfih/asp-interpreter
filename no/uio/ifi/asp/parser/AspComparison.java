package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    ArrayList<AspCompOpr> aspCompOprList = new ArrayList<>();
    ArrayList<AspTerm> aspTermList = new ArrayList<>();

    AspComparison(int lineNumber) {
        super(lineNumber);
    }

    public static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.aspTermList.add(AspTerm.parse(s));

            if (s.isCompOpr()) {
                ac.aspCompOprList.add(AspCompOpr.parse(s));
            } else {
                break;
            }
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    public void prettyPrint() {
        int i = 0;

        while (i < aspTermList.size()) {
            aspTermList.get(i).prettyPrint();

            if (i < aspCompOprList.size()) {
                aspCompOprList.get(i).prettyPrint();
            }
            
            i++;
        }
    }
}
