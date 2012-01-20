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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (!body.equals(rule.body)) return false;
        if (!head.equals(rule.head)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
