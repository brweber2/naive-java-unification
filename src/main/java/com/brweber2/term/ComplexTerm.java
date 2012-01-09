package com.brweber2.term;

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

    public int getArity() {
        return arity;
    }

    public List<Term> getTerms() {
        return terms;
    }

    @Override
    public String prettyPrint() {
        throw new RuntimeException("todo");
    }
}
