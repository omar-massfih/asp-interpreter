package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {
    long intValue;

    public RuntimeIntegerValue(long intValue) {
        this.intValue = intValue;
    }

    @Override
    public String typeName() {
        return "integer";
    }

    @Override
    public String toString() {
        return Long.toString(intValue);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return intValue != 0;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double) intValue;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue + runtimeValue.getIntValue("+ operator", where));
        }

        if (runtimeValue instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) intValue + runtimeValue.getFloatValue("+ operator", where));
        }

        return super.evalAdd(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue)) {
            return super.evalDivide(runtimeValue, where);
        }

        double operand = runtimeValue.getFloatValue("/ operator", where);

        if (operand == 0) {
            return super.evalDivide(runtimeValue, where);
        }

        return new RuntimeFloatValue((double) intValue / operand);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeIntegerValue || runtimeValue instanceof RuntimeFloatValue)) {
            return super.evalEqual(runtimeValue, where);
        }

        return new RuntimeBoolValue(getFloatValue("", where) == runtimeValue.getFloatValue("== operator", where));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeIntegerValue || runtimeValue instanceof RuntimeFloatValue)) {
            return super.evalGreater(runtimeValue, where);
        }

        return new RuntimeBoolValue(intValue > runtimeValue.getIntValue("> operator", where));
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeIntegerValue || runtimeValue instanceof RuntimeFloatValue)) {
            return super.evalGreaterEqual(runtimeValue, where);
        }

        return new RuntimeBoolValue(intValue >= runtimeValue.getIntValue(">= operator", where));
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue runtimeValue, AspSyntax where) {
        double operand;

        if (runtimeValue instanceof RuntimeFloatValue) {
            operand = runtimeValue.getFloatValue("// operator", where);
        } else if (runtimeValue instanceof RuntimeIntegerValue) {
            operand = runtimeValue.getIntValue("// operator", where);
        } else {
            return super.evalIntDivide(runtimeValue, where);
        }

        if (operand == 0) {
            return super.evalIntDivide(runtimeValue, where);
        }

        double result = Math.floor((double) intValue / operand);

        if (runtimeValue instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(result);
        } else {
            return new RuntimeIntegerValue((long) result);
        }
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue) {
            double operand = runtimeValue.getFloatValue("< operator", where);
            return new RuntimeBoolValue(getIntValue("", where) < operand);
        } else if (runtimeValue instanceof RuntimeIntegerValue) {
            long operand = runtimeValue.getIntValue("< operator", where);
            return new RuntimeBoolValue(getFloatValue("", where) < operand);
        }
    
        return super.evalLess(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue || runtimeValue instanceof RuntimeFloatValue) {
            long otherIntVal = runtimeValue.getIntValue("<= operator", where);
            return new RuntimeBoolValue(intValue <= otherIntVal);
        }

        return super.evalLessEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue)) {
            return super.evalModulo(runtimeValue, where);
        }

        if (runtimeValue instanceof RuntimeFloatValue) {
            double operand = runtimeValue.getFloatValue("% operand", where);
            double result = intValue - operand * Math.floor(intValue / operand);
            return new RuntimeFloatValue(result);
        } else {
            long operand = runtimeValue.getIntValue("% operand", where);
            long result = intValue % operand;
            return new RuntimeIntegerValue(result);
        }
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) (intValue * runtimeValue.getFloatValue("* operator", where)));
        } else if (runtimeValue instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue * runtimeValue.getIntValue("* operator", where));
        }

        return super.evalMultiply(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntegerValue(-intValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(intValue == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue != runtimeValue.getIntValue("!= operator", where));
        }

        if (runtimeValue instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((double) intValue != runtimeValue.getFloatValue("!= operator", where));
        }

        return super.evalNotEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntegerValue(intValue);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue - runtimeValue.getIntValue("- operator", where));
        }

        if (runtimeValue instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) (intValue - runtimeValue.getFloatValue("- operator", where)));
        }

        return super.evalSubtract(runtimeValue, where);
    }
}
