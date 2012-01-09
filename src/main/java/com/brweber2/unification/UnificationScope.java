package com.brweber2.unification;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnificationScope {
    private Map<Variable,Term> scope = new LinkedHashMap<Variable, Term>();

    public boolean set(Variable term1, Term value)
    {
        if ( scope.containsKey( term1 ) )
        {
            // todo this should call unify or .equals?
            return scope.get(term1).equals(value);
        }
        else
        {
            scope.put(term1,value);
            return true;
        }
    }

    Map<Variable, Term> getScope() {
        return scope;
    }
}
