package com.brweber2.unification;

import com.brweber2.term.Variable;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class SharedVariable {
    Variable queryVariable;
    Variable variable;

    public SharedVariable(Variable queryVariable, Variable variable) {
        this.queryVariable = queryVariable;
        this.variable = variable;
    }

    public Variable getQueryVariable() {
        return queryVariable;
    }

    public Variable getVariable() {
        return variable;
    }
}
