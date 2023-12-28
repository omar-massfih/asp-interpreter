package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    private AspFuncDef aspFuncDef;
    private String name;
    private ArrayList<RuntimeValue> parameters;
    private RuntimeScope runtimeScope;

    public RuntimeFunc(RuntimeValue runtimeValue, RuntimeScope curScope, AspFuncDef aspFuncDef, ArrayList<RuntimeValue> parameters) {
        this.aspFuncDef = aspFuncDef;
        this.name = runtimeValue.getStringValue(name, aspFuncDef);
        this.parameters = parameters;
        this.runtimeScope = curScope;
    }

    public RuntimeFunc(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String typeName() {
        return "function";
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        if (parameters.size() != actualParams.size()) {
            return super.evalFuncCall(actualParams, where);
        }
        
        Main.log.traceEval("Call function " + name + " with params " + actualParams.toString(), where);

        RuntimeValue runtimeValue = null;
        RuntimeScope runtimeScope2 = new RuntimeScope(runtimeScope);

        for (int i = 0; i < parameters.size(); i++) {
            runtimeValue = actualParams.get(i);

            runtimeScope2.assign(parameters.get(i).getStringValue("", where), runtimeValue);
        }
        
        try {
            runtimeValue = aspFuncDef.runFunction(runtimeScope2);
        } catch (RuntimeReturnValue runtimeReturnValue) {
            return runtimeReturnValue.value;
        }

        return runtimeValue;
    }
}
