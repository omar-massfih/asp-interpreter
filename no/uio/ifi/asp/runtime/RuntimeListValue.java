package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    private ArrayList<RuntimeValue> runtimeValueList;

    public RuntimeListValue(ArrayList<RuntimeValue> runtimeValueList) {
        this.runtimeValueList = runtimeValueList;
    }

    public RuntimeListValue() {
        runtimeValueList = new ArrayList<>();
    }

    public ArrayList<RuntimeValue> getRuntimeValueList() {
        return runtimeValueList;
    }

    public void addElem(RuntimeValue runtimeValue) {
        runtimeValueList.add(runtimeValue);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !runtimeValueList.isEmpty();
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < runtimeValueList.size(); i++) {
            stringBuilder.append(runtimeValueList.get(i));

            if (i != runtimeValueList.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeIntegerValue) {
            long times = runtimeValue.getIntValue("* operator", where);

            if (times < 0) {
                return super.evalMultiply(runtimeValue, where);
            }

            ArrayList<RuntimeValue> newList = new ArrayList<>();

            for (int t = 0; t < times; t++) {
                newList.addAll(runtimeValueList);
            }

            return new RuntimeListValue(newList);
        }

        return super.evalMultiply(runtimeValue, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeListValue) {
            RuntimeListValue rvsOther = (RuntimeListValue) runtimeValue;
            return new RuntimeBoolValue(runtimeValueList.equals(rvsOther.runtimeValueList));
        }

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue runtimeValue, AspSyntax where) {
        if (runtimeValue instanceof RuntimeListValue) {
            RuntimeListValue rvsOther = (RuntimeListValue) runtimeValue;
            return new RuntimeBoolValue(!(runtimeValueList.equals(rvsOther.runtimeValueList)));
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(runtimeValueList.isEmpty());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue runtimeValue, AspSyntax where) {
        if (!(runtimeValue instanceof RuntimeIntegerValue)) {
            return super.evalSubscription(runtimeValue, where);
        }

        long index = runtimeValue.getIntValue("int", where);

        if (index < 0 || index >= runtimeValueList.size()) {
            return super.evalSubscription(runtimeValue, where);
        }

        return runtimeValueList.get((int) index);
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        int index = (int) inx.getIntValue("int", where);

        if (index < 0 || index >= runtimeValueList.size()) {
            super.evalAssignElem(inx, val, where);
        } else {
            runtimeValueList.set(index, val);
        }
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(runtimeValueList.size());
    }
}
