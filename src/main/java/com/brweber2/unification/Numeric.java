package com.brweber2.unification;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Numeric implements Constant<Numeric>, Term {
    private final String number;

    public Numeric(String number) {
        this.number = number;
    }


    @Override
    public boolean same(Numeric numeric) {
        // todo fix me, this is pretty asinine ...
        return this.number.equals(numeric.number);
    }

    @Override
    public String prettyPrint() {
        return number;
    }
}
