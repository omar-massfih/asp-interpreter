package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    public ArrayList<RuntimeValue> rvs;

    public RuntimeListValue(ArrayList<RuntimeValue> rvs) {
        this.rvs = rvs;
    }

    public ArrayList<RuntimeValue> getRvs() {
        return rvs;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !rvs.isEmpty();
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < rvs.size(); i++) {
            sb.append(rvs.get(i));
            if (i != rvs.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            long times = v.getIntValue("* operator", where);
            if (times < 0) {
                return super.evalMultiply(v, where);
            }

            ArrayList<RuntimeValue> newList = new ArrayList<>();

            for (int t = 0; t < times; t++) {
                newList.addAll(rvs);
            }

            return new RuntimeListValue(newList);
        }

        return super.evalMultiply(v, where);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeListValue) {
            RuntimeListValue rvsOther = (RuntimeListValue) v;
            return new RuntimeBoolValue(rvs.equals(rvsOther.rvs));
        }

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeListValue) {
            RuntimeListValue rvsOther = (RuntimeListValue) v;
            return new RuntimeBoolValue(!(rvs.equals(rvsOther.rvs)));
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(rvs.isEmpty());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeIntegerValue)) {
            return super.evalSubscription(v, where);
        }

        long index = v.getIntValue("int", where);

        if (index < 0 || index >= rvs.size()) {
            return super.evalSubscription(v, where);
        }

        return rvs.get((int) index);
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        int index = (int) inx.getIntValue("int", where);

        if (index < 0 || index >= rvs.size()) {
            super.evalAssignElem(inx, val, where);
        } else {
            rvs.set(index, val);
        }
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(rvs.size());
    }
}
