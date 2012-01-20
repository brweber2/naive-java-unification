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
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("X"), new Variable("Y")), new ComplexTerm("just_ate", new Variable("X"), new Variable("Y"))) );
        // is_digesting(X,Y) :- just_ate(X,Z), is_digesting(Z,Y).
        knowledgeBase.rule( new Rule(new ComplexTerm("is_digesting", new Variable("X"), new Variable("Y")), new RuleAnd( new ComplexTerm("just_ate", new Variable("X"), new Variable("Z")), new ComplexTerm("is_digesting", new Variable("Z"), new Variable("Y")))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("mosquito"), new ComplexTerm("blood",new Atom("john"))) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("frog"), new Atom("mosquito")) );
        knowledgeBase.fact( new ComplexTerm("just_ate", new Atom("stork"), new Atom("frog")) );

        Unify unifier = new Unification();
        ProofSearch proofSearch = new ProofSearch(unifier,knowledgeBase);

        UnificationResult unificationResult2 = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("stork"), new Atom("mosquito")) );

        Assert.assertTrue( unificationResult2.getSuccess() == UnificationSuccess.NO );
        Assert.assertTrue( unificationResult2.getScope().isEmpty() );
        Assert.assertNull( unificationResult2.getNext() );

        UnificationResult unificationResult = proofSearch.ask( new ComplexTerm("is_digesting", new Atom("stork"), new Atom("mosquito")) );

        Assert.assertTrue( unificationResult.getSuccess() == UnificationSuccess.YES );
        Assert.assertTrue( unificationResult.getScope().isEmpty() );
        Assert.assertNull( unificationResult.getNext() );

    }
}
