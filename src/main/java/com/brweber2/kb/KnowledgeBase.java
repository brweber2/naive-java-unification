package com.brweber2.kb;

import com.brweber2.term.Term;
import com.brweber2.term.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class KnowledgeBase {
    
    private List<Term> terms = new ArrayList<Term>();
    private List<Rule> rules = new ArrayList<Rule>();
    
    public void fact( Term fact )
    {
        terms.add(fact);
    }
    
    public void rule( Rule rule )
    {
        rules.add(rule);
    }

    public List<Term> getTerms() {
        return terms;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
