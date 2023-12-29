package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspCompoundStmt extends AspStmt {
    AspCompoundStmt(int lineNumber) {
        super(lineNumber);
    }

    public static AspCompoundStmt parse(Scanner scanner) {
        enterParser("compound stmt");
        
        AspCompoundStmt aspCompundStmt = null;

        switch (scanner.curToken().kind) {
            case forToken:
                aspCompundStmt = AspForStmt.parse(scanner);
                break;
            case ifToken:
                aspCompundStmt = AspIfStmt.parse(scanner);
                break;
            case whileToken:
                aspCompundStmt = AspWhileStmt.parse(scanner);
                break;
            case defToken:
                aspCompundStmt = AspFuncDef.parse(scanner);
                break;
            default:
                parserError("No correct compound statement", scanner.curLineNum());
                break;
        }

        leaveParser("compound stmt");
        return aspCompundStmt;
    }
}
