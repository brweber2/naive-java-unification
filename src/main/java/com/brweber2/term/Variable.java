package com.brweber2.term;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Variable implements Term {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String prettyPrint() {
        return name;
    }
}
