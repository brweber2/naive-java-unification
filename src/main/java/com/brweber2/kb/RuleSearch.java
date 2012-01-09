package com.brweber2.kb;

import com.brweber2.term.Term;
import com.brweber2.term.rule.Rule;
import com.brweber2.unification.UnificationResult;
import com.brweber2.unification.Unify;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RuleSearch {

    private Unify unifier;
    private KnowledgeBase knowledgeBase;

    public RuleSearch(Unify unifier, KnowledgeBase knowledgeBase) {
        this.unifier = unifier;
        this.knowledgeBase = knowledgeBase;
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

    public UnificationResult ask(Term question, Rule rule) {
        return null; // todo
    }
}
