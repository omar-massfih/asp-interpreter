/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;
import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function len with params " + actualParams.toString(), where);
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });

        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function float with params " + actualParams.toString(), where);
                checkNumParams(actualParams, 1, "float", where);
                return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float", where));
            }
        });

        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function input with params " + actualParams.toString(), where);
                System.out.print(actualParams.get(0).getStringValue(getName(), where));
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });

        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function int with params " + actualParams.toString(), where);
                checkNumParams(actualParams, 1, "int", where);
                return new RuntimeIntegerValue(actualParams.get(0).getIntValue("int", where));
            }
        });

        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function print with params " + actualParams.toString(), where);

                for (RuntimeValue parameter : actualParams) {
                    System.out.print(parameter.getStringValue(null, where) + " ");
                }

                System.out.println();
                return new RuntimeNoneValue();
            }
        });

        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                checkNumParams(actualParams, 2, "range", where);
                long value1 = actualParams.get(0).getIntValue("1st parameter to range", where);
                long value2 = actualParams.get(1).getIntValue("2nd parameter to range", where);
                RuntimeListValue res = new RuntimeListValue();

                for (long i = value1; i < value2; ++i) {
                    res.addElem(new RuntimeIntegerValue(i));
                }

                return res;
            }
        });

        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                Main.log.traceEval("Call function str with params " + actualParams.toString(), where);
                checkNumParams(actualParams, 1, "str", where);
                return new RuntimeStringValue(actualParams.get(0).getStringValue("str", where));
            }
        });
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect) {
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
        }
    }
}
