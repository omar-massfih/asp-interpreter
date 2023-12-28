package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {
    private long intVal;

    public RuntimeIntegerValue(long intVal) {
        this.intVal = intVal;
    }

    @Override
    public String typeName() {
        return "integer";
    }

    @Override
    public String toString() {
        return Long.toString(intVal);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intVal;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return intVal != 0;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double) intVal;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intVal + v.getIntValue("+ operator", where));
        }

        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) intVal + v.getFloatValue("+ operator", where));
        }

        return super.evalAdd(v, where);
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue)) {
            return super.evalDivide(v, where);
        }

        double operand = v.getFloatValue("/ operator", where);

        if (operand == 0) {
            return super.evalDivide(v, where);
        }

        return new RuntimeFloatValue((double) intVal / operand);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeIntegerValue || v instanceof RuntimeFloatValue)) {
            return super.evalEqual(v, where);
        }

        return new RuntimeBoolValue(getFloatValue("", where) == v.getFloatValue("== operator", where));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeIntegerValue || v instanceof RuntimeFloatValue)) {
            return super.evalGreater(v, where);
        }

        return new RuntimeBoolValue(intVal > v.getIntValue("> operator", where));
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeIntegerValue || v instanceof RuntimeFloatValue)) {
            return super.evalGreaterEqual(v, where);
        }

        return new RuntimeBoolValue(intVal >= v.getIntValue(">= operator", where));
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

        double result = Math.floor((double) intVal / operand);

        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(result);
        } else {
            return new RuntimeIntegerValue((long) result);
        }
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            double operand = v.getFloatValue("< operator", where);
            return new RuntimeBoolValue(getIntValue("", where) < operand);
        } else if (v instanceof RuntimeIntegerValue) {
            long operand = v.getIntValue("< operator", where);
            return new RuntimeBoolValue(getFloatValue("", where) < operand);
        }
    
        return super.evalLess(v, where);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue || v instanceof RuntimeFloatValue) {
            long otherIntVal = v.getIntValue("<= operator", where);
            return new RuntimeBoolValue(intVal <= otherIntVal);
        }

        return super.evalLessEqual(v, where);
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeFloatValue || v instanceof RuntimeIntegerValue)) {
            return super.evalModulo(v, where);
        }

        if (v instanceof RuntimeFloatValue) {
            double operand = v.getFloatValue("% operand", where);
            double result = intVal - operand * Math.floor(intVal / operand);
            return new RuntimeFloatValue(result);
        } else {
            long operand = v.getIntValue("% operand", where);
            long result = intVal % operand;
            return new RuntimeIntegerValue(result);
        }
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) (intVal * v.getFloatValue("* operator", where)));
        } else if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intVal * v.getIntValue("* operator", where));
        }

        return super.evalMultiply(v, where);
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntegerValue(-intVal);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(intVal == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intVal != v.getIntValue("!= operator", where));
        }

        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((double) intVal != v.getFloatValue("!= operator", where));
        }

        return super.evalNotEqual(v, where);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntegerValue(intVal);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intVal - v.getIntValue("- operator", where));
        }

        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) (intVal - v.getFloatValue("- operator", where)));
        }

        return super.evalSubtract(v, where);
    }
}
