package com.brweber2.kb;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Variable;
import com.brweber2.unification.Unification;
import org.testng.annotations.Test;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class KnowledgeBaseTest {
    @Test
    public void simpleKnowledgeBase()
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.fact( new ComplexTerm("hello", 1, new Atom("dave")));
        knowledgeBase.fact( new ComplexTerm("hello", 1, new Atom("gary")));

        ProofSearch proofSearch = new ProofSearch(new Unification(),knowledgeBase);
        proofSearch.ask( new ComplexTerm("hello",1, new Variable("x")));
    }
}
