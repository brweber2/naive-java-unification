package com.brweber2.unification;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class UnificationResult {
    private final UnificationSuccess success;
    private Map<Variable,Term> scope = new HashMap<Variable,Term>();

    public UnificationResult(UnificationSuccess success) {
        this.success = success;
    }
    
    public UnificationSuccess set(Variable term1, Term value)
    {
        if ( scope.containsKey( term1 ) )
        {
            if ( scope.get(term1).equals(value) )
            {
                return UnificationSuccess.YES;
            }
            else
            {
                return UnificationSuccess.NO;
            }
        }
        else
        {
            scope.put(term1,value);
            return UnificationSuccess.YES;
        }
    }

    public UnificationSuccess getSuccess() {
        return success;
    }

    Map<Variable, Term> getScope() {
        return scope;
    }

    public void print()
    {
        if ( success == UnificationSuccess.YES )
        {
            if ( !scope.isEmpty() )
            {
                for (Variable variable : scope.keySet()) {
                    System.out.println(variable.prettyPrint() + ": " + scope.get(variable).prettyPrint() + "\n");
                }
            }
        }
        System.out.println(success.name().toLowerCase());
    }
}
