package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    private final String stringValue;

    public RuntimeStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String typeName() {
        return "string";
    }

    @Override
    public String toString() {
        return "\'" + stringValue + "\'";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !stringValue.isEmpty();
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return Long.parseLong(stringValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return Double.parseDouble(stringValue);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeStringValue) {
            return new RuntimeStringValue(stringValue + runtimeValue.getStringValue("string", where));
        }

        return super.evalAdd(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where) {
        return new RuntimeBoolValue(stringValue.equals(runtimeValue.getStringValue("", where)));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(runtimeValue.getStringValue("string", where)) > 0);
        }

        return super.evalGreater(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(runtimeValue.getStringValue("string", where)) >= 0);
        }

        return super.evalGreaterEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(runtimeValue.getStringValue("string", where)) < 0);
        }

        return super.evalLess(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(runtimeValue.getStringValue("string", where)) <= 0);
        }

        return super.evalLessEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            int repeatCount = (int) runtimeValue.getIntValue("int", where);
            String repeatedString = stringValue.repeat(Math.max(0, repeatCount));
            return new RuntimeStringValue(repeatedString);
        }

        return super.evalMultiply(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringValue.isEmpty());
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }

        return new RuntimeBoolValue(!stringValue.equals(runtimeValue.getStringValue("", where)));
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            int index = (int) runtimeValue.getIntValue("int", where);
    
            if (index >= 0 && index < stringValue.length()) {
                return new RuntimeStringValue(stringValue.charAt(index) + "");
            }
        }

        return super.evalSubscription(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(stringValue.length());
    }
}
