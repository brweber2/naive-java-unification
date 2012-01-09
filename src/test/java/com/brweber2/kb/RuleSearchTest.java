package com.brweber2.kb;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Variable;
import com.brweber2.term.rule.Rule;
import com.brweber2.term.rule.RuleAnd;
import com.brweber2.unification.Unification;
import com.brweber2.unification.UnificationResult;
import org.testng.annotations.Test;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class RuleSearchTest {
    @Test
    public void simpleKnowledgeBase()
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.fact( new ComplexTerm("hello", new Atom("dave")));
        knowledgeBase.fact( new ComplexTerm("hello", new Atom("gary")));
        knowledgeBase.fact( new ComplexTerm("bye", new Atom("gary")));

        // what(Z) :- hello(Z), bye(Z).
        knowledgeBase.rule( new Rule(new ComplexTerm("what",new Variable("Z")),new RuleAnd(new ComplexTerm("hello",new Variable("Z")), new ComplexTerm("bye",new Variable("Z")))) );

        RuleSearch ruleSearch = new RuleSearch(new Unification(),knowledgeBase);
        UnificationResult unificationResult = ruleSearch.ask(new ComplexTerm("what", new Variable("Z")), new Rule(new ComplexTerm("what", new Variable("Z")), new RuleAnd(new ComplexTerm("hello", new Variable("Z")), new ComplexTerm("bye", new Variable("Z")))));

    }
}
