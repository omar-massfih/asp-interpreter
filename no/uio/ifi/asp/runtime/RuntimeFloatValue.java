package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    private double floatValue;

    public RuntimeFloatValue(double floatVal) {
        this.floatValue = floatVal;
    }

    @Override
    String typeName() {
        return "float";
    }

    @Override
    public String toString() {
        return Double.toString(floatValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return floatValue != 0.0;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) Math.floor(floatValue);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("+ operator", where);
            return new RuntimeFloatValue(floatValue + operand);
        }

        return super.evalAdd(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("/ operator", where);
            return new RuntimeFloatValue(floatValue / operand);
        }

        return super.evalDivide(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where){
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue == runtimeValue.getFloatValue("", where));
        }

        if (runtimeValue instanceof RuntimeBoolValue || runtimeValue instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(getBoolValue("", where) == runtimeValue.getBoolValue("", where));
        }

        return super.evalEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("> operator", where);
            return new RuntimeBoolValue(floatValue > operand);
        }

        return super.evalGreater(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue(">= operator", where);
            return new RuntimeBoolValue(floatValue >= operand);
        }

        return super.evalGreaterEqual(runtimeValue, where);
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

        double result = Math.floor((double) getIntValue("", where) / operand);

        return new RuntimeFloatValue(result);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue) {
            double operand = runtimeValue.getFloatValue("< operator", where);
            return new RuntimeBoolValue(getIntValue("", where) < operand);
        } else if (runtimeValue instanceof RuntimeIntegerValue) {
            long operand = runtimeValue.getIntValue("< operator", where);
            return new RuntimeBoolValue(floatValue < operand);
        }
    
        return super.evalLess(runtimeValue, where);
    }
    
    @Override
    public RuntimeValue evalLessEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("<= operator", where);
            return new RuntimeBoolValue(floatValue <= operand);
        }

        return super.evalLessEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue)) {
            return super.evalModulo(runtimeValue, where);
        }
    
        double operand = runtimeValue.getFloatValue("% operand", where);

        double result = floatValue - operand * Math.floor(floatValue / operand);

        return new RuntimeFloatValue(result);
    }
    
    @Override
    public RuntimeValue evalMultiply(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("* operator", where);
            return new RuntimeFloatValue(floatValue * operand);
        }

        return super.evalMultiply(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-floatValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(floatValue == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("!= operator", where);
            return new RuntimeBoolValue(floatValue != operand);
        }

        if (runtimeValue instanceof RuntimeBoolValue || runtimeValue instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(getBoolValue("", where) == runtimeValue.getBoolValue("", where));
        }

        return super.evalNotEqual(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(floatValue);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeFloatValue || runtimeValue instanceof RuntimeIntegerValue) {
            double operand = runtimeValue.getFloatValue("- operator", where);
            return new RuntimeFloatValue(floatValue - operand);
        }

        return super.evalSubtract(runtimeValue, where);
    }
}
