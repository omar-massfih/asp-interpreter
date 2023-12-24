/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 */

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private Stack<Integer> indents = new Stack<>();
	private final int TABDIST = 4;
	private boolean indentationError = false;

	public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
							new FileInputStream(fileName),
							"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}

	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}

	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}

	public void readNextToken() {
		if (!curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}

	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// Add indent, dedent and eof tokens
		if (line == null) {
			while (!indents.isEmpty() && indents.peek() > 0) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken));
			}

			curLineTokens.add(new Token(TokenKind.eofToken));

			for (Token t : curLineTokens) {
				Main.log.noteToken(t);
			}
		}

		// Skip line if empty or starts with a comment
		if (line.trim().isEmpty() || line.trim().charAt(0) == '#') {
			return;
		}

		// Detect indentation
		line = expandLeadingTabs(line);
		int n = findIndent(line);

		if (n > indents.peek()) {
			indents.push(n);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		} else {
			while (!indents.isEmpty() && n < indents.peek()) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
			if (indents.isEmpty() || n != indents.peek()) {
				indentationError = true;
				scannerError("Indentation error.");
			}
		}

		// Add all tokens in the line
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (c == ' ') {
				continue;
			} else if (c == '#') {
				break;
			} else if (isDigit(c)) {
				if (c == '0' && (i + 1 < line.length() && line.charAt(i + 1) != '.')) {
					Token t = new Token(TokenKind.integerToken, curLineNum());
					t.integerLit = Integer.parseInt(Character.toString(c));
					curLineTokens.add(t);
					continue;
				}

				// Integer and float

				int j = i + 1;

				while (j < line.length() && (isDigit(line.charAt(j)) || line.charAt(j) == '.')) {
					j++;
				}

				String str = line.substring(i, j);

				if (str.contains(".")) {
					Token t = new Token(TokenKind.floatToken, curLineNum());
					t.floatLit = Double.parseDouble(str);
					curLineTokens.add(t);
				} else {
					Token t = new Token(TokenKind.integerToken, curLineNum());
					t.integerLit = Integer.parseInt(str);
					curLineTokens.add(t);
				}

				i = j - 1;
			} else if (isLetterAZ(c)) {
				// Name and Keywords

				int j = i + 1;

				while (j < line.length() && (isLetterAZ(line.charAt(j)) || isDigit(line.charAt(j)))) {
					j++;
				}

				String subStr = line.substring(i, j);

				if (subStr.equals("and")) {
					curLineTokens.add(new Token(TokenKind.andToken, curLineNum()));
				} else if (subStr.equals("def")) {
					curLineTokens.add(new Token(TokenKind.defToken, curLineNum()));
				} else if (subStr.equals("elif")) {
					curLineTokens.add(new Token(TokenKind.elifToken, curLineNum()));
				} else if (subStr.equals("else")) {
					curLineTokens.add(new Token(TokenKind.elseToken, curLineNum()));
				} else if (subStr.equals("False")) {
					curLineTokens.add(new Token(TokenKind.falseToken, curLineNum()));
				} else if (subStr.equals("False")) {
					curLineTokens.add(new Token(TokenKind.falseToken, curLineNum()));
				} else if (subStr.equals("for")) {
					curLineTokens.add(new Token(TokenKind.forToken, curLineNum()));
				} else if (subStr.equals("global")) {
					curLineTokens.add(new Token(TokenKind.globalToken, curLineNum()));
				} else if (subStr.equals("if")) {
					curLineTokens.add(new Token(TokenKind.ifToken, curLineNum()));
				} else if (subStr.equals("in")) {
					curLineTokens.add(new Token(TokenKind.inToken, curLineNum()));
				} else if (subStr.equals("None")) {
					curLineTokens.add(new Token(TokenKind.noneToken, curLineNum()));
				} else if (subStr.equals("not")) {
					curLineTokens.add(new Token(TokenKind.notToken, curLineNum()));
				} else if (subStr.equals("or")) {
					curLineTokens.add(new Token(TokenKind.orToken, curLineNum()));
				} else if (subStr.equals("pass")) {
					curLineTokens.add(new Token(TokenKind.passToken, curLineNum()));
				} else if (subStr.equals("return")) {
					curLineTokens.add(new Token(TokenKind.returnToken, curLineNum()));
				} else if (subStr.equals("True")) {
					curLineTokens.add(new Token(TokenKind.trueToken, curLineNum()));
				} else if (subStr.equals("try")) {
					curLineTokens.add(new Token(TokenKind.tryToken, curLineNum()));
				} else if (subStr.equals("while")) {
					curLineTokens.add(new Token(TokenKind.whileToken, curLineNum()));
				} else {
					Token t = new Token(TokenKind.nameToken, curLineNum());
					t.name = subStr;
					curLineTokens.add(t);
				}

				i = j - 1;
			} else if (c == '"' || c == '\'') {
				// String token

				int j = i + 1;
				char anfoerselTegn = c == '"' ? '"' : '\'';

				while (j < line.length() && line.charAt(j) != anfoerselTegn) {
					j++;
				}

				String subStr = "";

				if (j >= line.length()) {
					subStr = line.substring(i, j);
				} else {
					subStr = line.substring(i, j + 1);
				}

				Token t = new Token(TokenKind.stringToken, curLineNum());
				t.stringLit = subStr.substring(1, subStr.length() - 1);
				curLineTokens.add(t);

				i = j;
			} else {
				// Delimiters and Operators
				int j = i + 1;

				if (c == '*') {
					curLineTokens.add(new Token(TokenKind.astToken, curLineNum()));
				} else if (c == '>') {
					if (j < line.length() && line.charAt(j) == '=') {
						curLineTokens.add(new Token(TokenKind.greaterEqualToken, curLineNum()));
						i = j;
					} else {
						curLineTokens.add(new Token(TokenKind.greaterToken, curLineNum()));
					}
				} else if (c == '<') {
					if (j < line.length() && line.charAt(j) == '=') {
						curLineTokens.add(new Token(TokenKind.lessEqualToken, curLineNum()));
						i = j;
					} else {
						curLineTokens.add(new Token(TokenKind.lessToken, curLineNum()));
					}
				} else if (c == '-') {
					curLineTokens.add(new Token(TokenKind.minusToken, curLineNum()));
				} else if (c == '%') {
					curLineTokens.add(new Token(TokenKind.percentToken, curLineNum()));
				} else if (c == '+') {
					curLineTokens.add(new Token(TokenKind.plusToken, curLineNum()));
				} else if (c == '/') {
					if (j < line.length() && line.charAt(j) == '/') {
						curLineTokens.add(new Token(TokenKind.doubleSlashToken, curLineNum()));
						i = j;
					} else {
						curLineTokens.add(new Token(TokenKind.slashToken, curLineNum()));
					}
				} else if (c == '=') {
					if (j < line.length() && line.charAt(j) == '=') {
						curLineTokens.add(new Token(TokenKind.doubleEqualToken, curLineNum()));
						i = j;
					} else {
						curLineTokens.add(new Token(TokenKind.equalToken, curLineNum()));
					}
				} else if (c == '!') {
					if (j < line.length() && line.charAt(j) == '=') {
						curLineTokens.add(new Token(TokenKind.notEqualToken, curLineNum()));
						i = j;
					}
				} else if (c == ':') {
					curLineTokens.add(new Token(TokenKind.colonToken, curLineNum()));
				} else if (c == ',') {
					curLineTokens.add(new Token(TokenKind.commaToken, curLineNum()));
				} else if (c == '{') {
					curLineTokens.add(new Token(TokenKind.leftBraceToken, curLineNum()));
				} else if (c == '[') {
					curLineTokens.add(new Token(TokenKind.leftBracketToken, curLineNum()));
				} else if (c == '(') {
					curLineTokens.add(new Token(TokenKind.leftParToken, curLineNum()));
				} else if (c == '}') {
					curLineTokens.add(new Token(TokenKind.rightBraceToken, curLineNum()));
				} else if (c == ']') {
					curLineTokens.add(new Token(TokenKind.rightBracketToken, curLineNum()));
				} else if (c == ')') {
					curLineTokens.add(new Token(TokenKind.rightParToken, curLineNum()));
				} else if (c == ';') {
					curLineTokens.add(new Token(TokenKind.semicolonToken, curLineNum()));
				}
			}
		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken, curLineNum()));

		for (Token t : curLineTokens) {
			Main.log.noteToken(t);
		}
	}

	public int curLineNum() {
		return sourceFile != null ? sourceFile.getLineNumber() : 0;
	}

	private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ')
			indent++;
		return indent;
	}

	private String expandLeadingTabs(String s) {
        StringBuilder newLine = new StringBuilder();
        int spaceCount = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                spaceCount++;
                newLine.append(' ');
            } else if (s.charAt(i) == '\t') {
                int spacesToAdd = 4 - (spaceCount % 4);
                for (int j = 0; j < spacesToAdd; j++) {
                    newLine.append(' ');
                }
                spaceCount += spacesToAdd;
            } else {
                newLine.append(s.substring(i));
                break;
            }
        }

        return newLine.toString();
    }

	private boolean isLetterAZ(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean anyEqualToken() {
		for (Token t : curLineTokens) {
			if (t.kind == equalToken)
				return true;
			if (t.kind == semicolonToken)
				return false;
		}
		return false;
	}
}
