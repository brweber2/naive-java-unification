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

import java.util.logging.Logger;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RuleSearch {

    private static final Logger log = Logger.getLogger(RuleSearch.class.getName());
    
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
                log.fine("unified and rule " + ruleBody + " with scope: " + scope );
                return finalResult.getNext();
            }
            log.fine("Unable to unify and rule " + ruleBody + " with scope: " + scope);
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
                log.fine("unified or rule " + ruleBody + " with scope: " + scope );
                return finalResult.getNext();
            }
            log.fine("Unable to unify or rule " + ruleBody + " with scope: " + scope);
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

        log.fine("asking: " + question + " with rule: " + rule + " with scope: " + scope);

//        UnificationResult headResult = proofSearch.getUnifier().unify(new UnificationScope(scope), question, rule.getHead() );
        UnificationResult headResult = proofSearch.getUnifier().unify(new UnificationScope(), question, rule.getHead() );
        UnificationResult baseResult = new UnificationResult();
        UnificationResult finalResult = baseResult;
        while ( headResult != null )
        {
            if ( headResult.getSuccess() == UnificationSuccess.YES )
            {
                log.finer("head unified");
                // now we have to see if all the conditions in body hold
                UnificationResult bodyResult = unifyRuleBody( new UnificationScope( headResult.getUnifyScope() ), rule.getBody() );
                while ( bodyResult != null )
                {
                    if ( bodyResult.getSuccess() == UnificationSuccess.YES )
                    {
                        log.finer("body unified, checking scopes...");
                        if ( consistent( scope, headResult.getUnifyScope(), bodyResult.getUnifyScope() ) )
                        {
                            log.finer("answered " + question + " with " + rule + " with scope " + scope);
                            merge(scope,headResult.getUnifyScope(),bodyResult.getUnifyScope());
                            finalResult = finalResult.next( new UnificationResult( scope, question, rule.getBody() ) );
                        }
                    }
                    bodyResult = bodyResult.getNext();
                }
                log.fine("unable to answer " + question + " with " + rule + " with scope " + scope);
            }
            else
            {
                log.fine("head did not unify");
            }
            headResult = headResult.getNext();
        }
        if( baseResult != finalResult )
        {
            return baseResult.getNext();
        }
        return baseResult;
    }

    private void merge( UnificationScope scope, UnificationScope headScope, UnificationScope bodyScope )
    {
        for ( Variable variable : headScope.keys() )
        {
            Term term = headScope.grab( variable );
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
            Term term = headScope.grab( variable );
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
