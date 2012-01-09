package com.brweber2.term.rule;

import com.brweber2.term.Term;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Rule {
    
    Term head;
    RuleBody body;


    public Rule(Term head, RuleBody body) {
        this.head = head;
        this.body = body;
    }

    public Term getHead() {
        return head;
    }

    public RuleBody getBody() {
        return body;
    }
}
