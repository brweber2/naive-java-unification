package com.brweber2.term.rule;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class RuleAnd implements RuleBody {
    RuleBody left;
    RuleBody right;

    public RuleAnd(RuleBody left, RuleBody right) {
        this.left = left;
        this.right = right;
    }

    public RuleBody getLeft() {
        return left;
    }

    public RuleBody getRight() {
        return right;
    }
}
