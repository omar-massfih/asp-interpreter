/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.scanner;

import java.util.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Token {
	public TokenKind kind;
	public String name, stringLit;
	public long integerLit;
	public double floatLit;
	public int lineNum;

	Token(TokenKind k) {
		this(k, 0);
	}

	Token(TokenKind k, int lNum) {
		kind = k;
		lineNum = lNum;
	}

	void checkResWords() {
		if (kind != nameToken) {
			return;
		}

		for (TokenKind tk : EnumSet.range(andToken, yieldToken)) {
			if (name.equals(tk.image)) {
				kind = tk;
				break;
			}
		}
	}

	public String showInfo() {
		String t = kind + " token";

		if (lineNum > 0) {
			t += " on line " + lineNum;
		}

		switch (kind) {
			case floatToken:
				t += ": " + floatLit;
				break;
			case integerToken:
				t += ": " + integerLit;
				break;
			case nameToken:
				t += ": " + name;
				break;
			case stringToken:
				if (stringLit.indexOf('"') >= 0)
					t += ": '" + stringLit + "'";
				else
					t += ": " + '"' + stringLit + '"';
				break;
			case andToken:
				break;
			case asToken:
				break;
			case assertToken:
				break;
			case astToken:
				break;
			case breakToken:
				break;
			case classToken:
				break;
			case colonToken:
				break;
			case commaToken:
				break;
			case continueToken:
				break;
			case dedentToken:
				break;
			case defToken:
				break;
			case delToken:
				break;
			case doubleEqualToken:
				break;
			case doubleSlashToken:
				break;
			case elifToken:
				break;
			case elseToken:
				break;
			case eofToken:
				break;
			case equalToken:
				break;
			case exceptToken:
				break;
			case falseToken:
				break;
			case finallyToken:
				break;
			case forToken:
				break;
			case fromToken:
				break;
			case globalToken:
				break;
			case greaterEqualToken:
				break;
			case greaterToken:
				break;
			case ifToken:
				break;
			case importToken:
				break;
			case inToken:
				break;
			case indentToken:
				break;
			case isToken:
				break;
			case lambdaToken:
				break;
			case leftBraceToken:
				break;
			case leftBracketToken:
				break;
			case leftParToken:
				break;
			case lessEqualToken:
				break;
			case lessToken:
				break;
			case minusToken:
				break;
			case newLineToken:
				break;
			case noneToken:
				break;
			case nonlocalToken:
				break;
			case notEqualToken:
				break;
			case notToken:
				break;
			case orToken:
				break;
			case passToken:
				break;
			case percentToken:
				break;
			case plusToken:
				break;
			case raiseToken:
				break;
			case returnToken:
				break;
			case rightBraceToken:
				break;
			case rightBracketToken:
				break;
			case rightParToken:
				break;
			case semicolonToken:
				break;
			case slashToken:
				break;
			case trueToken:
				break;
			case tryToken:
				break;
			case whileToken:
				break;
			case withToken:
				break;
			case yieldToken:
				break;
			default:
				break;
		}

		return t;
	}

	@Override
	public String toString() {
		return kind.toString();
	}
}
