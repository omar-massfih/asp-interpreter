/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.runtime;

// For part 4:

public class RuntimeReturnValue extends Exception {
    public int lineNum;
    public RuntimeValue value;

    public RuntimeReturnValue(RuntimeValue v, int lNum) {
        value = v;
        lineNum = lNum;
    }
}
