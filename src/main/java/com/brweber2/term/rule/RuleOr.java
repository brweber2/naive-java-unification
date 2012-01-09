package com.brweber2.term.rule;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class RuleOr {
    RuleBody left;
    RuleBody right;

    public RuleOr(RuleBody left, RuleBody right) {
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
