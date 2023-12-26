package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> aspFactorPrefixList = new ArrayList<>();
    ArrayList<AspPrimary> aspPrimaryList = new ArrayList<>();
    ArrayList<AspFactorOpr> aspFactorOprList = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor aspFactor = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()) {
                aspFactor.aspFactorPrefixList.add(AspFactorPrefix.parse(s));
            } else {
                aspFactor.aspFactorPrefixList.add(null);
            }

            aspFactor.aspPrimaryList.add(AspPrimary.parse(s));

            if (s.isFactorOpr()) {
                aspFactor.aspFactorOprList.add(AspFactorOpr.parse(s));
            } else {
                break;
            }
        }

        leaveParser("factor");
        return aspFactor;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < aspPrimaryList.size(); i++) {
            if (i < aspFactorPrefixList.size() && aspFactorPrefixList.get(i) != null) {
                aspFactorPrefixList.get(i).prettyPrint();
            }

            aspPrimaryList.get(i).prettyPrint();

            if (i < aspFactorOprList.size()) {
                aspFactorOprList.get(i).prettyPrint();
            }
        }
    }
}
