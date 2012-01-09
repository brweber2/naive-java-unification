package com.brweber2.unification;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.term.*;
import com.brweber2.term.rule.Rule;
import com.brweber2.term.rule.RuleAnd;
import com.brweber2.term.rule.RuleBody;
import com.brweber2.term.rule.RuleOr;

import java.util.List;
import java.util.UUID;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Unification implements Unify {

    public UnificationResult unify(Term term1, Term term2) {
        return unify(new UnificationScope(),term1,term2);
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
        else if ( term1 instanceof Variable ) 
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

    @Override
    public UnificationResult unify(KnowledgeBase knowledgeBase, Term term1, Rule rule) {
        return null;  //todo implement me
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
