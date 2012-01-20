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

    public ComplexTerm(String functor, Term ... terms) {
        this.functor = functor;
        if ( terms != null )
        {
            this.arity = terms.length;
            Collections.addAll( this.terms, terms );
        }
        else
        {
            this.arity = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexTerm that = (ComplexTerm) o;

        if (arity != that.arity) return false;
        if (!functor.equals(that.functor)) return false;
        if (!terms.equals(that.terms)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = functor.hashCode();
        result = 31 * result + arity;
        result = 31 * result + terms.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ComplexTerm{" +
                "functor='" + functor + '\'' +
                ", arity=" + arity +
                ", terms=" + terms +
                '}';
    }
}
