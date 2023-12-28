package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    private final String stringVal;

    public RuntimeStringValue(String stringVal) {
        this.stringVal = stringVal;
    }

    @Override
    public String typeName() {
        return "string";
    }

    @Override
    public String toString() {
        return "\'" + stringVal + "\'";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringVal;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !stringVal.isEmpty();
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return Long.parseLong(stringVal);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return Double.parseDouble(stringVal);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeStringValue(stringVal + v.getStringValue("string", where));
        }

        return super.evalAdd(v, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(stringVal.equals(v.getStringValue("", where)));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringVal.compareTo(v.getStringValue("string", where)) > 0);
        }

        return super.evalGreater(v, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringVal.compareTo(v.getStringValue("string", where)) >= 0);
        }

        return super.evalGreaterEqual(v, where);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringVal.compareTo(v.getStringValue("string", where)) < 0);
        }

        return super.evalLess(v, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringVal.compareTo(v.getStringValue("string", where)) <= 0);
        }

        return super.evalLessEqual(v, where);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            int repeatCount = (int) v.getIntValue("int", where);
            String repeatedString = stringVal.repeat(Math.max(0, repeatCount));
            return new RuntimeStringValue(repeatedString);
        }

        return super.evalMultiply(v, where);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringVal.isEmpty());
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }

        return new RuntimeBoolValue(!stringVal.equals(v.getStringValue("", where)));
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            int index = (int) v.getIntValue("int", where);
    
            if (index >= 0 && index < stringVal.length()) {
                return new RuntimeStringValue(stringVal.charAt(index) + "");
            }
        }

        return super.evalSubscription(v, where);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(stringVal.length());
    }
}
