package com.brweber2.unification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class ComplexTerm implements Term  {
    
    private final String functor;
    private final int arity;
    private final List<Term> terms = new ArrayList<Term>();

    public ComplexTerm(String functor, int arity, Term ... terms) {
        this.functor = functor;
        this.arity = arity;
        if ( terms != null )
        {
            Collections.addAll( this.terms, terms );
        }
    }

    public boolean sameFunctor(ComplexTerm b) {
        return this.functor.equals(b.functor);
    }

    public boolean sameArity(ComplexTerm b) {
        return this.arity == b.arity;
    }

    boolean argsUnify(Unify unify, ComplexTerm b) {
        for (int i = 0; i < arity; i++ ) {
            UnificationResult match = unify.unify( this.terms.get(i), b.terms.get(i));
            switch (match.getSuccess()) {
                case YES:
                    break;
                case NO:
                    return false;
            }
        }
        return true;
    }

    @Override
    public String prettyPrint() {
        throw new RuntimeException("todo");
    }
}
