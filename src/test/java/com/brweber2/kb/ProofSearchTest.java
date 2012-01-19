package com.brweber2.kb;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Variable;
import com.brweber2.unification.Unification;
import com.brweber2.unification.UnificationResult;
import com.brweber2.unification.UnificationSuccess;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class ProofSearchTest {
    @Test
    public void simpleKnowledgeBase()
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.fact( new ComplexTerm("hello", new Atom("dave")));
        knowledgeBase.fact( new ComplexTerm("hello", new Atom("gary")));

        ProofSearch proofSearch = new ProofSearch(new Unification(),knowledgeBase);
        UnificationResult unificationResult = proofSearch.ask(new ComplexTerm("hello", new Variable("x")));
        Assert.assertTrue(unificationResult.getSuccess() == UnificationSuccess.YES);
        Assert.assertTrue(unificationResult.getScope().has(new Variable("x")));
        Assert.assertEquals(unificationResult.getScope().get(new Variable("x")), new Atom("dave") );
        Assert.assertNotNull(unificationResult.getNext());

        UnificationResult next = unificationResult.getNext();
        Assert.assertTrue(next.getSuccess() == UnificationSuccess.YES);
        Assert.assertTrue(next.getScope().has(new Variable("x")));
        Assert.assertEquals(next.getScope().get(new Variable("x")), new Atom("gary") );
        Assert.assertNull(next.getNext());
    }
}
