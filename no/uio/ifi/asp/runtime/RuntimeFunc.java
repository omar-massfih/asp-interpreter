package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    private AspFuncDef afd;
    private String name;
    private ArrayList<RuntimeValue> parameters;
    private RuntimeScope rs;

    public RuntimeFunc(RuntimeValue v, RuntimeScope curScope, AspFuncDef afd, ArrayList<RuntimeValue> parameters) {
        this.afd = afd;
        this.name = v.getStringValue(name, afd);
        this.parameters = parameters;
        this.rs = curScope;
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

        RuntimeValue rv = null;
        RuntimeScope rs2 = new RuntimeScope(rs);

        for (int i = 0; i < parameters.size(); i++) {
            rv = actualParams.get(i);

            rs2.assign(parameters.get(i).getStringValue("", where), rv);
        }
        
        try {
            rv = afd.runFunction(rs2);
        } catch (RuntimeReturnValue rrv) {
            return rrv.value;
        }

        return rv;
    }
}
