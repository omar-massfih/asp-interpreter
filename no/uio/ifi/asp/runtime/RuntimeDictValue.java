package no.uio.ifi.asp.runtime;

import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    public HashMap<String, RuntimeValue> dict;

    public RuntimeDictValue(HashMap<String, RuntimeValue> dict) {
        this.dict = dict;
    }

    @Override
    String typeName() {
        return "dictionary";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        for (Map.Entry<String, RuntimeValue> entry : dict.entrySet()) {
            sb.append("'").append(entry.getKey()).append("'").append(": ").append(entry.getValue()).append(", ");
        }

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !dict.isEmpty();
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return toString();
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(dict.isEmpty());
    }

    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }

        if (v instanceof RuntimeDictValue) {
            v.equals(this);
        }

        return super.evalEqual(v, where);
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue key, AspSyntax where) {
        if (!(key instanceof RuntimeStringValue)) {
            super.evalSubscription(key, where);
        }

        String keyString = key.getStringValue("string", where);

        RuntimeValue rv = dict.get(keyString);

        if (rv != null) {
            return rv;
        }

        return super.evalSubscription(key, where);
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        dict.put(inx.getStringValue("string", where), val);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(dict.size());
    }
}