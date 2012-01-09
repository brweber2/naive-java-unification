package com.brweber2.term;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Atom implements Term {
    
    private final String atom;

    public Atom(String atom) {
        this.atom = atom;
    }

    // todo should we just use equals???
    public boolean same(Atom atom) {
        return this.atom.equals(atom.atom);
    }

    @Override
    public String prettyPrint() {
        return atom;
    }
}
