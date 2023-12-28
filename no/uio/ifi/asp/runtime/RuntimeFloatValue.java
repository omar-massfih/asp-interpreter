package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    private double floatVal;

    public RuntimeFloatValue(double floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    String typeName() {
        return "float";
    }

    @Override
    public String toString() {
        return Double.toString(floatVal);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return floatVal != 0.0;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatVal;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) Math.floor(floatVal);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("+ operator", where);
            return new RuntimeFloatValue(floatVal + operand);
        }

        return super.evalAdd(v, where);
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("/ operator", where);
            return new RuntimeFloatValue(floatVal / operand);
        }

        return super.evalDivide(v, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatVal == v.getFloatValue("", where));
        }

        if (v instanceof RuntimeBoolValue || v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(getBoolValue("", where) == v.getBoolValue("", where));
        }

        return super.evalEqual(v, where);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("> operator", where);
            return new RuntimeBoolValue(floatVal > operand);
        }

        return super.evalGreater(v, where);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue(">= operator", where);
            return new RuntimeBoolValue(floatVal >= operand);
        }

        return super.evalGreaterEqual(v, where);
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        double operand;

        if (v instanceof RuntimeFloatValue) {
            operand = v.getFloatValue("// operator", where);
        } else if (v instanceof RuntimeIntegerValue) {
            operand = v.getIntValue("// operator", where);
        } else {
            return super.evalIntDivide(v, where);
        }

        if (operand == 0) {
            return super.evalIntDivide(v, where);
        }

        double result = Math.floor((double) getIntValue("", where) / operand);

        return new RuntimeFloatValue(result);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            double operand = v.getFloatValue("< operator", where);
            return new RuntimeBoolValue(getIntValue("", where) < operand);
        } else if (v instanceof RuntimeIntegerValue) {
            long operand = v.getIntValue("< operator", where);
            return new RuntimeBoolValue(floatVal < operand);
        }
    
        return super.evalLess(v, where);
    }
    
    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("<= operator", where);
            return new RuntimeBoolValue(floatVal <= operand);
        }

        return super.evalLessEqual(v, where);
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue)) {
            return super.evalModulo(v, where);
        }
    
        double operand = v.getFloatValue("% operand", where);

        double result = floatVal - operand * Math.floor(floatVal / operand);

        return new RuntimeFloatValue(result);
    }
    
    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("* operator", where);
            return new RuntimeFloatValue(floatVal * operand);
        }

        return super.evalMultiply(v, where);
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-floatVal);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(floatVal == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("!= operator", where);
            return new RuntimeBoolValue(floatVal != operand);
        }

        if (v instanceof RuntimeBoolValue || v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(getBoolValue("", where) == v.getBoolValue("", where));
        }

        return super.evalNotEqual(v, where);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(floatVal);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue) {
            double operand = v.getFloatValue("- operator", where);
            return new RuntimeFloatValue(floatVal - operand);
        }

        return super.evalSubtract(v, where);
    }
}
