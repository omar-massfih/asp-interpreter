/*
 * Original code in this file by Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo, 2021.
 * Modifications made by Omar Massfih in 2023.
 * 
 */

package no.uio.ifi.asp.runtime;

// For part 4:

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeScope {
	private RuntimeScope outerScope;
	private HashMap<String, RuntimeValue> decls = new HashMap<>();
	private ArrayList<String> globalNames = new ArrayList<>();

	public RuntimeScope() {
		// Used by the library (and when testing expressions in part 3)
		outerScope = null;
	}

	public RuntimeScope(RuntimeScope outerScope) {
		// Used by all user scopes
		this.outerScope = outerScope;
	}

	public void assign(String id, RuntimeValue runtimeValue) {
		if (globalNames.contains(id)) {
			Main.globalScope.decls.put(id, runtimeValue);
		} else {
			decls.put(id, runtimeValue);
		}
	}

	public RuntimeValue find(String id, AspSyntax where) {
		if (globalNames.contains(id)) {
			RuntimeValue runtimeValue = Main.globalScope.decls.get(id);

			if (runtimeValue != null) {
				return runtimeValue;
			}
		} else {
			RuntimeScope scope = this;

			while (scope != null) {
				RuntimeValue runtimeValue = scope.decls.get(id);

				if (runtimeValue != null) {
					return runtimeValue;
				}
				
				scope = scope.outerScope;
			}
		}
		
		RuntimeValue.runtimeError("Name " + id + " not defined!", where);
		return null; // Required by the compiler.
	}

	public boolean hasDefined(String id) {
		return decls.get(id) != null;
	}

	public boolean hasGlobalName(String id) {
		return globalNames.contains(id);
	}

	public void registerGlobalName(String id) {
		globalNames.add(id);
	}
}
