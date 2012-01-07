package com.brweber2.unification;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Atom implements Constant<Atom>, Term {
    
    private final String atom;

    public Atom(String atom) {
        this.atom = atom;
    }

    // todo should we just use equals???
    @Override
    public boolean same(Atom atom) {
        return this.atom.equals(atom.atom);
    }

    @Override
    public String prettyPrint() {
        return atom;
    }
}
