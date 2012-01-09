package com.brweber2.unification;

import com.brweber2.term.*;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Unification implements Unify {
    @Override
    public UnificationResult unify(Term term1, Term term2) {
        return unify(new UnificationResult(UnificationSuccess.YES),term1,term2);
    }

    public UnificationResult unify(UnificationResult soFar, Term term1, Term term2) {
        if ( term1 instanceof Atom && term2 instanceof Atom )
        {
            if ( ((Atom)term1).same( (Atom) term2 ) )
            {
                return new UnificationResult(UnificationSuccess.YES);
            }
            return new UnificationResult(UnificationSuccess.NO);
        }
        else if ( term1 instanceof Numeric && term2 instanceof Numeric )
        {
            if ( ((Numeric)term1).same( (Numeric) term2 ) )
            {
                return new UnificationResult(UnificationSuccess.YES);
            }
            return new UnificationResult(UnificationSuccess.NO);
        }
        else if ( term1 instanceof Variable ) 
        {
            if ( soFar.set((Variable)term1, term2) == UnificationSuccess.YES )
            {
                return soFar;
            }
            return new UnificationResult( UnificationSuccess.NO );
        }
        else if ( term2 instanceof Variable )
        {
            if ( soFar.set((Variable)term2, term1) == UnificationSuccess.YES )
            {
                return soFar;
            }
            return new UnificationResult( UnificationSuccess.NO );
        }
        else if ( term1 instanceof ComplexTerm && term2 instanceof ComplexTerm )
        {
            ComplexTerm a = (ComplexTerm) term1;
            ComplexTerm b = (ComplexTerm) term2;
            // same functor and arity
            if ( a.sameFunctor(b) && a.sameArity(b) )
            {
                // args unify
                if ( argsUnify(soFar, a, b) )
                {
                    // variables are compatible
                    return soFar;
                }
            }
            return new UnificationResult(UnificationSuccess.NO);
        }
        else
        {
            return new UnificationResult(UnificationSuccess.NO);
        }
    }

    boolean argsUnify(UnificationResult soFar, ComplexTerm a, ComplexTerm b) {
        for (int i = 0; i < a.getArity(); i++ ) {
            UnificationResult match = unify(soFar, a.getTerms().get(i), b.getTerms().get(i));
            switch (match.getSuccess()) {
                case YES:
                    break;
                case NO:
                    return false;
            }
        }
        return true;
    }
}
