package com.brweber2.unification;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class UnificationResult {
    private final UnificationSuccess success;
    private final UnificationScope scope;
    private UnificationResult next;
    // unifying terms
    private Term term1;
    private Term term2;

    public UnificationResult() {
        this.success = UnificationSuccess.NO;
        this.scope = new UnificationScope();
    }

    public UnificationResult( UnificationScope scope, Term term1, Term term2) {
        this.success = UnificationSuccess.YES;        
        this.scope = scope;
        this.term1 = term1;
        this.term2 = term2;
    }

    public UnificationResult next( UnificationResult result )
    {
        this.next = result;
        return result;
    }

    public UnificationResult getNext() {
        return next;
    }

    public UnificationSuccess getSuccess() {
        return success;
    }

    public Map<Variable, Term> getScope() {
        return scope.getScope();
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        if ( success == UnificationSuccess.YES )
        {
            if ( !scope.getScope().isEmpty() )
            {
                for (Variable variable : scope.getScope().keySet()) {
                    str.append(variable.prettyPrint() + ": " + scope.getScope().get(variable).prettyPrint() + "\n");
                }
            }
        }
        str.append(success.name().toLowerCase());
        str.append("\n");
        return str.toString();
    }

    public void print()
    {
        System.out.println(toString());
    }
}
