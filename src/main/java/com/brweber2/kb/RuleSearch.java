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

    /*
    public List<UnificationResult> unify(KnowledgeBase knowledgeBase, Term term1, Rule rule)
    {
        List<Variable> variablesFromQuery = term1.getVariables();
        Term newTerm = term1.copyYourself();
        Rule newRule = new Rule(rule.getHead(),rule.getBody());
        for (Variable variableFromQuery : variablesFromQuery) {
            String randomVariableName = UUID.randomUUID().toString();
            newTerm.replaceAny(variableFromQuery).with(randomVariableName);
            newRule.replaceAny(variableFromQuery).with(randomVariableName);
        }
        // todo implement backtracking!!! and checking the actual body of the rule!!!!
        UnificationResult headResult = unify(new UnificationResult(UnificationSuccess.NO),newTerm,newRule.getHead());
        if ( headResult.getSuccess() == UnificationSuccess.NO )
        {
            return headResult;
        }
        RuleBody nextRule = newRule.getBody();
        return processPartOfRule( knowledgeBase, nextRule );
    }

    private List<UnificationResult> processPartOfRule( KnowledgeBase knowledgeBase, RuleBody ruleBody )
    {
        if ( ruleBody instanceof RuleAnd )
        {
            RuleAnd ruleAnd = (RuleAnd) ruleBody;
            UnificationResult leftResult = processPartOfRule( knowledgeBase, ruleAnd.getLeft() );
            UnificationResult rightResult = processPartOfRule(knowledgeBase, ruleAnd.getRight());
            if ( leftResult.getSuccess() == UnificationSuccess.YES && rightResult.getSuccess() == UnificationSuccess.YES )
            {
                // success
            }
            else
            {
                // nope
            }
        }
        else if ( ruleBody instanceof RuleOr )
        {
            RuleAnd ruleAnd = (RuleAnd) ruleBody;
            UnificationResult leftResult = processPartOfRule( knowledgeBase, ruleAnd.getLeft() );
            UnificationResult rightResult = processPartOfRule( knowledgeBase, ruleAnd.getRight() );
            if ( leftResult.getSuccess() == UnificationSuccess.YES || rightResult.getSuccess() == UnificationSuccess.YES )
            {
                // success
            }
            else
            {
                // nope
            }
        }
        else
        {
            Term term = (Term) ruleBody;
            return knowledgeBase.ask( term );
        }
    }
    */
    
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
                return new UnificationResult(scope, left, right );
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
                RuleBody l = null;
                if ( leftResult.getSuccess() == UnificationSuccess.YES )
                {
                    l = left;
                }
                RuleBody r = null;
                if ( rightResult.getSuccess() == UnificationSuccess.YES )
                {
                    r = right;
                }
                return new UnificationResult(scope, l, r );
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
