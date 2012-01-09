package com.brweber2.unification;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Unification implements Unify {

    public UnificationResult unify(Term term1, Term term2) {
        return unify(new UnificationScope(),term1,term2);
    }

    public UnificationResult unify(UnificationScope scope, Term term1, Term term2) {
        UnificationResult success = new UnificationResult( scope, term1, term2 );
        if ( term1 instanceof Atom && term2 instanceof Atom )
        {
            if ( ((Atom)term1).same( (Atom) term2 ) )
            {
                return success;
            }
        }
        else if ( term1 instanceof Numeric && term2 instanceof Numeric )
        {
            if ( ((Numeric)term1).same( (Numeric) term2 ) )
            {
                return success;
            }
        }
        else if ( term1 instanceof Variable)
        {
            if ( scope.set((Variable)term1, term2) )
            {
                return success;
            }
        }
        else if ( term2 instanceof Variable )
        {
            if ( scope.set((Variable)term2, term1) )
            {
                return success;
            }
        }
        else if ( term1 instanceof ComplexTerm && term2 instanceof ComplexTerm )
        {
            ComplexTerm a = (ComplexTerm) term1;
            ComplexTerm b = (ComplexTerm) term2;
            // same functor and arity
            if ( a.sameFunctor(b) && a.sameArity(b) )
            {
                // args unify
                if ( argsUnify(scope, a, b) )
                {
                    // variables are compatible
                    return success;
                }
            }
        }
        // did NOT unify
        return new UnificationResult();
    }

    boolean argsUnify(UnificationScope scope, ComplexTerm a, ComplexTerm b) {
        for (int i = 0; i < a.getArity(); i++ ) {
            UnificationResult match = unify(scope, a.getTerms().get(i), b.getTerms().get(i));
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
