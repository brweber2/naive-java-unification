package com.brweber2.kb;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Variable;
import com.brweber2.term.rule.Rule;
import com.brweber2.term.rule.RuleAnd;
import com.brweber2.unification.Unification;
import com.brweber2.unification.UnificationResult;
import com.brweber2.unification.UnificationSuccess;
import com.brweber2.unification.Unify;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RecursionTest {

    @Test
    public void recursiveRule()
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        // is_digesting(X,Y) :- just_ate(X,Y).
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("M"), new Variable("N")), new ComplexTerm("just_ate", new Variable("M"), new Variable("N"))) );
        // is_digesting(X,Y) :- just_ate(X,Z), is_digesting(Z,Y).
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("X"), new Variable("Y")), new RuleAnd( new ComplexTerm("just_ate", new Variable("X"), new Variable("Z")), new ComplexTerm("is_digesting", new Variable("Z"), new Variable("Y")))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("mosquito"), new ComplexTerm("blood",new Atom("john"))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("frog"), new Atom("mosquito")) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("stork"), new Atom("frog")) );

        Unify unifier = new Unification();
        ProofSearch proofSearch = new ProofSearch(unifier,knowledgeBase);

        // ?- is_digesting(mosquito,stork).
        UnificationResult unificationResult2 = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("mosquito"), new Atom("stork")) );

        Assert.assertTrue( unificationResult2.getSuccess() == UnificationSuccess.NO );
        Assert.assertTrue( unificationResult2.getScope().isEmpty() );
        Assert.assertNull( unificationResult2.getNext() );

        UnificationResult unificationResult = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("stork"), new Atom("mosquito")) );

        Assert.assertTrue( unificationResult.getSuccess() == UnificationSuccess.YES );
        Assert.assertFalse( unificationResult.getScope().isEmpty() );
        Assert.assertTrue(unificationResult.getScope().has(new Variable("X")));
        Assert.assertEquals(unificationResult.getScope().get(new Variable("X")), new Atom("stork") );
        Assert.assertTrue(unificationResult.getScope().has(new Variable("Y")));
        Assert.assertEquals(unificationResult.getScope().get(new Variable("Y")), new Atom("mosquito") );
//        Assert.assertTrue(unificationResult.getScope().has(new Variable("Z")));
//        Assert.assertEquals(unificationResult.getScope().get(new Variable("Z")), new Atom("frog") );
        Assert.assertNull( unificationResult.getNext() );

    }

    @Test
    public void sameVariables()
    {
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        // is_digesting(X,Y) :- just_ate(X,Y).
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("X"), new Variable("Y")), new ComplexTerm("just_ate", new Variable("X"), new Variable("Y"))) );
        // is_digesting(X,Y) :- just_ate(X,Z), is_digesting(Z,Y).
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("X"), new Variable("Y")), new RuleAnd( new ComplexTerm("just_ate", new Variable("X"), new Variable("Z")), new ComplexTerm("is_digesting", new Variable("Z"), new Variable("Y")))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("mosquito"), new ComplexTerm("blood",new Atom("john"))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("frog"), new Atom("mosquito")) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("stork"), new Atom("frog")) );

        Unify unifier = new Unification();
        ProofSearch proofSearch = new ProofSearch(unifier,knowledgeBase);

        UnificationResult unificationResult2 = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("mosquito"), new Atom("stork")) );

        Assert.assertTrue( unificationResult2.getSuccess() == UnificationSuccess.NO );
        Assert.assertTrue( unificationResult2.getScope().isEmpty() );
        Assert.assertNull( unificationResult2.getNext() );

        UnificationResult unificationResult = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("stork"), new Atom("mosquito")) );

        Assert.assertTrue( unificationResult.getSuccess() == UnificationSuccess.YES );
        Assert.assertFalse( unificationResult.getScope().isEmpty() );
        Assert.assertTrue(unificationResult.getScope().has(new Variable("X")));
        Assert.assertEquals(unificationResult.getScope().get(new Variable("X")), new Atom("stork") );
        Assert.assertTrue(unificationResult.getScope().has(new Variable("Y")));
        Assert.assertEquals(unificationResult.getScope().get(new Variable("Y")), new Atom("mosquito") );
//        Assert.assertTrue(unificationResult.getScope().has(new Variable("Z")));
//        Assert.assertEquals(unificationResult.getScope().get(new Variable("Z")), new Atom("frog") );
        Assert.assertNull( unificationResult.getNext() );

    }
}
