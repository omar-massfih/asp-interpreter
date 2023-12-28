/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNoneValue extends RuntimeValue {
    @Override
    String typeName() {
        return "None";
    }

    @Override
    public String toString() {
        return "None";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return false;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where) {
        return new RuntimeBoolValue(runtimeValue instanceof RuntimeNoneValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        return new RuntimeBoolValue(!(runtimeValue instanceof RuntimeNoneValue));
    }
}
