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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleAnd ruleAnd = (RuleAnd) o;

        if (!left.equals(ruleAnd.left)) return false;
        if (!right.equals(ruleAnd.right)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
