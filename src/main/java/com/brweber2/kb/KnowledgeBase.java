package com.brweber2.kb;

import com.brweber2.term.Term;
import com.brweber2.unification.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class KnowledgeBase {
    
    private List<Term> terms = new ArrayList<Term>();
    private Unify unifier = new Unification();
    
    public void fact( Term fact )
    {
        terms.add(fact);
    }
    
    public void rule( Term rule )
    {
        throw new RuntimeException("not implemented!");
    }
    
    public void ask( Term question )
    {
        for (Term term : terms) {
            UnificationResult result = unifier.unify( question, term );
            if ( result.getSuccess() == UnificationSuccess.YES )
            {
                result.print();
            }
        }
        System.out.println("done");
    }
}
