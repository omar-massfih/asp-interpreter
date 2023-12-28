/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public abstract class RuntimeValue {
    abstract String typeName();

    public String showInfo() {
        return toString();
    }

    // For parts 3 and 4:

    public boolean getBoolValue(String what, AspSyntax where) {
        runtimeError("Type error: " + what + " is not a Boolean!", where);
        return false; // Required by the compiler!
    }

    public double getFloatValue(String what, AspSyntax where) {
        runtimeError("Type error: " + what + " is not a float!", where);
        return 0; // Required by the compiler!
    }

    public long getIntValue(String what, AspSyntax where) {
        runtimeError("Type error: " + what + " is not an integer!", where);
        return 0; // Required by the compiler!
    }

    public String getStringValue(String what, AspSyntax where) {
        runtimeError("Type error: " + what + " is not a text string!", where);
        return null; // Required by the compiler!
    }

    // For part 3:

    public RuntimeValue evalAdd(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'+' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalDivide(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'/' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'==' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalGreater(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'>' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalGreaterEqual(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'>=' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalIntDivide(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'//' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalLen(AspSyntax where) {
        runtimeError("'len' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalLess(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'<' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalLessEqual(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'<=' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalModulo(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'%' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalMultiply(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalNegate(AspSyntax where) {
        runtimeError("Unary '-' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalNot(AspSyntax where) {
        runtimeError("'not' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'!=' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalPositive(AspSyntax where) {
        runtimeError("Unary '+' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalSubscription(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("Subscription '[...]' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    public RuntimeValue evalSubtract(RuntimeValue runtimeValue, AspSyntax where) {
        runtimeError("'-' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }

    // General:

    public static void runtimeError(String message, int lineNumber) {
        Main.error("Asp runtime error on line " + lineNumber + ": " + message);
    }

    public static void runtimeError(String message, AspSyntax where) {
        runtimeError(message, where.lineNum);
    }

    // For part 4:

    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        runtimeError("Assigning to an element not allowed for " + typeName() + "!", where);
    }

    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
            AspSyntax where) {
        runtimeError("Function call '(...)' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }
}
