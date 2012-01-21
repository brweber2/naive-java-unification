package com.brweber2.kb;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.term.rule.Rule;
import com.brweber2.term.rule.RuleAnd;
import com.brweber2.term.rule.RuleBody;
import com.brweber2.term.rule.RuleOr;
import com.brweber2.unification.UnificationResult;
import com.brweber2.unification.UnificationScope;
import com.brweber2.unification.UnificationSuccess;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RuleSearch {

    private ProofSearch proofSearch;

    public RuleSearch(ProofSearch proofSearch) {
        this.proofSearch = proofSearch;
    }
    
    public UnificationResult unifyRuleBody( UnificationScope scope, RuleBody ruleBody )
    {
        if ( ruleBody instanceof RuleAnd )
        {
            RuleAnd ruleAnd = (RuleAnd) ruleBody;
            RuleBody left = ruleAnd.getLeft();
            RuleBody right = ruleAnd.getRight();
            UnificationResult finalResult = new UnificationResult();
            UnificationResult currentResult = finalResult;
            UnificationResult leftResult = unifyRuleBody(new UnificationScope(scope), left);
            while ( leftResult != null )
            {
                if ( leftResult.getSuccess() == UnificationSuccess.YES )
                {
                    UnificationResult rightResult = unifyRuleBody(new UnificationScope(leftResult.getScope()),right);
                    while ( rightResult != null )
                    {
                        if ( rightResult.getSuccess() == UnificationSuccess.YES )
                        {
                            currentResult = currentResult.next(new UnificationResult(rightResult.getScope(), left, right));
                        }
                        rightResult = rightResult.getNext();
                    }
                }

                leftResult = leftResult.getNext();
            }
            if ( finalResult != currentResult )
            {
                System.out.println("unified and rule " + ruleBody + " with scope: " + scope );
                return finalResult.getNext();
            }
            System.out.println("Unable to unify and rule " + ruleBody + " with scope: " + scope);
            return finalResult;
        }
        else if ( ruleBody instanceof RuleOr )
        {
            RuleOr ruleOr = (RuleOr) ruleBody;
            RuleBody left = ruleOr.getLeft();
            RuleBody right = ruleOr.getRight();

            UnificationResult finalResult = new UnificationResult();
            UnificationResult currentResult = finalResult;
            
            UnificationResult leftResult = unifyRuleBody( new UnificationScope(scope), left );
            while ( leftResult != null )
            {
                if ( leftResult.getSuccess() == UnificationSuccess.YES )
                {
                    currentResult = currentResult.next(new UnificationResult(leftResult.getScope(),left,right));
                }

                leftResult = leftResult.getNext();
            }
            UnificationResult rightResult = unifyRuleBody( new UnificationScope(scope), right );
            while ( rightResult != null )
            {
                if ( rightResult.getSuccess() == UnificationSuccess.YES )
                {
                    currentResult = currentResult.next(new UnificationResult(rightResult.getScope(),left,right));
                }
                rightResult = rightResult.getNext();
            }
            if ( finalResult != currentResult )
            {
                System.out.println("unified or rule " + ruleBody + " with scope: " + scope );
                return finalResult.getNext();
            }
            System.out.println("Unable to unify or rule " + ruleBody + " with scope: " + scope);
            return finalResult;
        }
        else
        {
            return proofSearch.ask(scope, (Term) ruleBody);
        }
    }

    public UnificationResult ask(Term question, Rule rule) {
        return ask( new UnificationScope(), question, rule );
    }
    
    public UnificationResult ask(UnificationScope scope, Term question, Rule rule) {
        // does the question unify with head?

        System.out.println("asking: " + question + " with rule: " + rule + " with scope: " + scope);

//        UnificationResult headResult = proofSearch.getUnifier().unify(new UnificationScope(scope), question, rule.getHead() );
        UnificationResult headResult = proofSearch.getUnifier().unify(new UnificationScope(), question, rule.getHead() );
        if ( headResult.getSuccess() == UnificationSuccess.YES )
        {
            System.out.println("head unified");
            // now we have to see if all the conditions in body hold
            UnificationResult bodyResult = unifyRuleBody( new UnificationScope( headResult.getUnifyScope() ), rule.getBody() );
            if ( bodyResult.getSuccess() == UnificationSuccess.YES )
            {
                System.out.println("body unified, checking scopes...");
                if ( consistent( scope, headResult.getUnifyScope(), bodyResult.getUnifyScope() ) )
                {
                    System.out.println("answered " + question + " with " + rule + " with scope " + scope);
                    merge(scope,headResult.getUnifyScope(),bodyResult.getUnifyScope());
                    return new UnificationResult( scope, question, rule.getBody() );
                }
            }
            System.out.println("unable to answer " + question + " with " + rule + " with scope " + scope);
            return new UnificationResult();
        }
        else
        {
            System.out.println("head did not unify");
            return headResult;
        }
    }

    private void merge( UnificationScope scope, UnificationScope headScope, UnificationScope bodyScope )
    {
        for ( Variable variable : headScope.keys() )
        {
            Term term = headScope.get( variable );
            if ( term == null )
            {
                Term value = bodyScope.get( variable );
                scope.set( variable, value );
            }
            else if ( term instanceof Variable )
            {
                Term value = bodyScope.get( (Variable) term );
                scope.set( variable, value );
            }
            else
            {
                scope.set( variable, term );
            }
        }
    }

    private boolean consistent( UnificationScope scope, UnificationScope headScope, UnificationScope bodyScope )
    {
        for ( Variable variable : headScope.keys() )
        {
            Term term = headScope.get( variable );
            if ( term == null )
            {
                Term value = bodyScope.get( variable );
                if ( scope.has( variable ) && !scope.get( variable ).equals( value ) )
                {
                    return false;
                }
            }
            else if ( term instanceof Variable )
            {
                Term value = bodyScope.get( (Variable) term );
                if ( scope.has( variable ) && !scope.get( variable ).equals( value ) )
                {
                    return false;
                }
            }
            else
            {
                if ( scope.has( variable ) && !scope.get( variable ).equals( term ) )
                {
                    return false;
                }
            }
        }
        return true;
    }
}
