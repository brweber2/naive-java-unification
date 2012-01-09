package com.brweber2.kb;

import com.brweber2.term.Term;
import com.brweber2.term.rule.Rule;
import com.brweber2.unification.UnificationResult;
import com.brweber2.unification.UnificationSuccess;
import com.brweber2.unification.Unify;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class ProofSearch {

    private Unify unifier;
    private KnowledgeBase knowledgeBase;
    private RuleSearch ruleSearch;

    public ProofSearch(Unify unifier, KnowledgeBase knowledgeBase) {
        this.unifier = unifier;
        this.knowledgeBase = knowledgeBase;
        this.ruleSearch = new RuleSearch(unifier,knowledgeBase);
    }

    public UnificationResult ask( Term question )
    {
        UnificationResult result = new UnificationResult();
        UnificationResult baseResult = result;
        for (Term term : knowledgeBase.getTerms()) {
            UnificationResult thisResult = unifier.unify(question, term);
            if ( thisResult.getSuccess() == UnificationSuccess.YES )
            {
                result.next(thisResult);
                result = thisResult;
            }
        }
        for (Rule rule : knowledgeBase.getRules()) {
            UnificationResult thisResult = ruleSearch.ask( question, rule );
            if ( thisResult.getSuccess() == UnificationSuccess.YES )
            {
                result.next(thisResult);
                result = thisResult;
            }
        }
        return baseResult.getNext();
    }

    public void askAndPrint( Term question )
    {
        printUnificationResult( ask( question ) );
    }

    public void printUnificationResult( UnificationResult result )
    {
        if ( result.getSuccess() == UnificationSuccess.YES )
        {
            while ( result.getNext() != null )
            {
                System.out.println(result.toString());
                result = result.getNext();
            }
        }
        System.out.println(result.getSuccess().name().toLowerCase());
    }
}
