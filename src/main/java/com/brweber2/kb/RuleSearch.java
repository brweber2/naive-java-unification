package com.brweber2.kb;

import com.brweber2.term.Term;
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
    
    private UnificationResult merge(UnificationResult left, UnificationResult right)
    {
        return new UnificationResult(left.getScope().merge(right.getScope()),null,null);
    }
    
    public UnificationResult unifyRuleBody( UnificationScope scope, RuleBody ruleBody )
    {
        if ( ruleBody instanceof RuleAnd )
        {
            RuleAnd ruleAnd = (RuleAnd) ruleBody;
            RuleBody left = ruleAnd.getLeft();
            UnificationResult leftResult = unifyRuleBody( scope, left );
            RuleBody right = ruleAnd.getRight();
            UnificationResult rightResult = unifyRuleBody( scope, right );
            if ( leftResult.getSuccess() == UnificationSuccess.YES && rightResult.getSuccess() == UnificationSuccess.YES )
            {
                UnificationResult union = new UnificationResult();
                while ( leftResult != null )
                {
                    while ( rightResult != null )
                    {
                        if ( isConsistent(leftResult, rightResult))
                        {
                            union.next(merge(leftResult,rightResult));
                        }
                        rightResult = rightResult.getNext();
                    }
                    leftResult = leftResult.getNext();
                }
                return union.getNext();
            }
            return new UnificationResult();
        }
        else if ( ruleBody instanceof RuleOr )
        {
            RuleOr ruleAnd = (RuleOr) ruleBody;
            RuleBody left = ruleAnd.getLeft();
            UnificationResult leftResult = unifyRuleBody( scope, left );
            RuleBody right = ruleAnd.getRight();
            UnificationResult rightResult = unifyRuleBody( scope, right );
            if ( leftResult.getSuccess() == UnificationSuccess.YES || rightResult.getSuccess() == UnificationSuccess.YES )
            {
                UnificationResult l = null;
                if ( leftResult.getSuccess() == UnificationSuccess.YES )
                {
                    l = leftResult;
                }
                UnificationResult r = null;
                if ( rightResult.getSuccess() == UnificationSuccess.YES )
                {
                    r = rightResult;
                }
                return merge(l,r);
            }
            return new UnificationResult();
        }
        else
        {
            return proofSearch.ask((Term) ruleBody);
        }
    }

    public UnificationResult ask(Term question, Rule rule) {
        // does the question unify with head?
        
        UnificationResult headResult = proofSearch.getUnifier().unify(question, rule.getHead() );
        if ( headResult.getSuccess() == UnificationSuccess.YES )
        {
            // now we have to see if all the conditions in body hold
            return unifyRuleBody( headResult.getUnifyScope(), rule.getBody() );
        }
        else
        {
            return headResult;
        }
    }
}
