package com.brweber2.unification;

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

        knowledgeBase.ask( new ComplexTerm("hello",1, new Variable("x")));
    }
}
