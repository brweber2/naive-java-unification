package com.brweber2.unification;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnifyTest {
    @Test
    public void unifyAtoms()
    {
        Atom atom1 = new Atom("foo");
        Atom atom2 = new Atom("foo");
        
        Unify unifier = new Unification();
        UnificationResult result = unifier.unify(atom1,atom2);
        Assert.assertTrue(result.getSuccess() == UnificationSuccess.YES);
        result.print();
        
        Atom atom3 = new Atom("bar");
        UnificationResult result2 = unifier.unify(atom1,atom3);
        Assert.assertTrue(result2.getSuccess() == UnificationSuccess.NO);
        result2.print();
    }

    @Test
    public void unifyNumerics()
    {
        Numeric numeric1 = new Numeric("1.43");
        Numeric numeric2 = new Numeric("1.43");

        Unify unifier = new Unification();
        UnificationResult result = unifier.unify(numeric1,numeric2);
        Assert.assertTrue(result.getSuccess() == UnificationSuccess.YES);
        result.print();

        Numeric numeric3 = new Numeric("5");
        UnificationResult result2 = unifier.unify(numeric1,numeric3);
        Assert.assertTrue(result2.getSuccess() == UnificationSuccess.NO);
        result2.print();
    }
    
    @Test
    public void unifyVariables()
    {
        Variable variable1 = new Variable("foo");
        Atom atom1 = new Atom("quux");

        Unify unifier = new Unification();
        UnificationResult result = unifier.unify(variable1,atom1);
        Assert.assertTrue( result.getSuccess() == UnificationSuccess.YES );
        result.print();
        Assert.assertTrue( result.getScope().containsKey(variable1) );
    }
}
